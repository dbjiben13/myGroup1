package Windows;

import Manager.SoftwarePasswordManager;
import Util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 登录窗口
 * Created by Attect on 2015/12/12 0012.
 */
public class LoginWindow{
    private static final String TEXT_WINDOW_TITLE = "身份确认";
    private static final String TEXT_INTER_PASSWORD = "请输入密码:";
    private static final int WINDOW_WIDTH = 300;//窗口宽度
    private static final int WINDOW_HEIGHT = 100; //窗口高度
    private JFrame rootFrame;
    private JPanel rootPanel;
    private JLabel interPasswordText;
    private JPasswordField passwordInputField;
    private JButton loginButton;

    private LoginEventListener loginEventListener;

    /**
     * 初始化并显示窗口
     */
    public void init(){
        rootFrame = new JFrame(TEXT_WINDOW_TITLE);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rootFrame.setBounds(Utils.centerWindowStartX(WINDOW_WIDTH), Utils.centerWindowStartY(WINDOW_HEIGHT),WINDOW_WIDTH,WINDOW_HEIGHT);

        rootPanel = new JPanel(new FlowLayout());
        rootFrame.setContentPane(rootPanel);

        interPasswordText = new JLabel(TEXT_INTER_PASSWORD);
        rootPanel.add(interPasswordText);

        passwordInputField = new JPasswordField(16);
        passwordInputField.setEchoChar('#');
        passwordInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER) startLogin();
            }
        });
        rootPanel.add(passwordInputField);

        loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startLogin();
            }
        });
        rootPanel.add(loginButton);
        rootFrame.setVisible(true);

        //新用户
        if(SoftwarePasswordManager.getInstance().isNewUser() && loginEventListener!=null) loginEventListener.onNewUser();
    }

    private void startLogin(){
        String password = new String(passwordInputField.getPassword());
        if(password.length() > 0 && password.length() < 17){ //密码长度有效才作出反应
            if(SoftwarePasswordManager.getInstance().isNewUser()){ //新用户
                try{
                    if(SoftwarePasswordManager.getInstance().setPassword(password)){
                        if(loginEventListener != null){
                            loginEventListener.onCorrect(); //密码正确
                        }
                    }else {
                        if(loginEventListener != null){
                            loginEventListener.onWrong("文件操作错误");
                        }
                    }
                }catch (SoftwarePasswordManager.SoftwarePasswordException ee){
                    ee.printStackTrace();
                    if(loginEventListener != null){
                        loginEventListener.onWrong(ee.getMessage());
                    }
                }
            }else{
                if(SoftwarePasswordManager.getInstance().checkPassword(password)){
                    if(loginEventListener!=null){
                        loginEventListener.onCorrect();
                    }
                }else{
                    loginEventListener.onWrong("密码错误");
                }
            }
        }else{
            if(loginEventListener != null) loginEventListener.onWrong("输入不规范");
        }
    }

    /**
     * 获得本窗口事件监听器
     * @return LoginEventListener
     */
    public LoginEventListener getLoginEventListener() {
        return loginEventListener;
    }

    /**
     * 设置本窗口时间监听器
     * @param loginEventListener 窗口监听器
     */
    public void setLoginEventListener(LoginEventListener loginEventListener) {
        this.loginEventListener = loginEventListener;
    }

    public void setVisible(boolean visible){
        rootFrame.setVisible(visible);
    }

    public void dispose(){
        rootFrame.dispose();
    }

    /**
     * 事件监听器接口
     */
    public interface LoginEventListener{
        void onClose();
        void onCorrect();
        void onWrong(String message);
        void onNewUser();
    }
}
