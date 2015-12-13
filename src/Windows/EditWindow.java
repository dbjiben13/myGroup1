package Windows;

import Manager.DBManager;
import Model.PasswordModel;
import Util.Utils;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 添加/编辑窗口
 * Created by attect on 15-12-13.
 */
public class EditWindow {
    private static final String TEXT_WINDOW_TITLE = "编辑";
    private static final int WINDOW_WIDTH = 300;//窗口宽度
    private static final int WINDOW_HEIGHT = 220; //窗口高度

    private JFrame rootFrame;
    private JPanel rootPanel;

    private PasswordModel target = new PasswordModel();// 操作目标

    private JLabel inputKeywordText;            //输入关键字提示文字
    private JLabel inputUserNameText;           //输入用户名提示文字
    private JLabel inputPasswordText;           //输入密码提示文字
    private JLabel reInputPasswordText;         //再次输入密码提示文字
    private final int TEXT_LABEL_START_X    = 10;
    private final int TEXT_LABEL_WIDTH      = 100;
    private final int TEXT_LABEL_HEIGHT     = 24;

    private JTextField keywordTextField;        //关键字输入框
    private JTextField userNameTextField;       //用户名输入框
    private JPasswordField passwordTextField;   //密码输入框
    private JPasswordField rePasswordTextField; //确认密码输入框
    private final int INPUT_FIELD_START_X   = 80;
    private final int INPUT_FIELD_WIDTH     = 200;
    private final int INPUT_FIELD_HEIGHT    = TEXT_LABEL_HEIGHT;

    private JButton okButton,cancelButton;

    private DataEventListener dataEventListener = null;

    public EditWindow(){}

    public EditWindow(PasswordModel target){
        this.target = target;
    }

    public void init(){
        rootFrame = new JFrame(TEXT_WINDOW_TITLE);
        rootFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        rootFrame.setBounds(Utils.centerWindowStartX(WINDOW_WIDTH), Utils.centerWindowStartY(WINDOW_HEIGHT),WINDOW_WIDTH,WINDOW_HEIGHT);

        rootPanel = new JPanel(null);
        rootFrame.setContentPane(rootPanel);

        inputKeywordText = new JLabel("关键字");
        inputKeywordText.setBounds(TEXT_LABEL_START_X,10,TEXT_LABEL_WIDTH,TEXT_LABEL_HEIGHT);
        rootPanel.add(inputKeywordText);

        inputUserNameText = new JLabel("用户名");
        inputUserNameText.setBounds(TEXT_LABEL_START_X,inputKeywordText.getY() + TEXT_LABEL_HEIGHT + 10,TEXT_LABEL_WIDTH,TEXT_LABEL_HEIGHT);
        rootPanel.add(inputUserNameText);

        inputPasswordText = new JLabel("密码");
        inputPasswordText.setBounds(TEXT_LABEL_START_X,inputUserNameText.getY() + TEXT_LABEL_HEIGHT + 10,TEXT_LABEL_WIDTH,TEXT_LABEL_HEIGHT);
        rootPanel.add(inputPasswordText);

        reInputPasswordText = new JLabel("确认密码");
        reInputPasswordText.setBounds(TEXT_LABEL_START_X,inputPasswordText.getY() + TEXT_LABEL_HEIGHT + 10,TEXT_LABEL_WIDTH,TEXT_LABEL_HEIGHT);
        rootPanel.add(reInputPasswordText);

        keywordTextField = new JTextField();
        keywordTextField.setBounds(INPUT_FIELD_START_X,10,INPUT_FIELD_WIDTH,INPUT_FIELD_HEIGHT);
        if(target != null) keywordTextField.setText(target.getKeyword());
        rootPanel.add(keywordTextField);

        userNameTextField = new JTextField();
        userNameTextField.setBounds(INPUT_FIELD_START_X,keywordTextField.getY() + INPUT_FIELD_HEIGHT + 10,INPUT_FIELD_WIDTH,INPUT_FIELD_HEIGHT);
        if(target != null) userNameTextField.setText(target.getUsername());
        rootPanel.add(userNameTextField);

        passwordTextField = new JPasswordField();
        passwordTextField.setEchoChar('#');
        passwordTextField.setBounds(INPUT_FIELD_START_X,userNameTextField.getY() + INPUT_FIELD_HEIGHT + 10,INPUT_FIELD_WIDTH,INPUT_FIELD_HEIGHT);
        if(target != null) passwordTextField.setText(target.getPassword());
        rootPanel.add(passwordTextField);

        rePasswordTextField = new JPasswordField();
        rePasswordTextField.setEchoChar('#');
        rePasswordTextField.setBounds(INPUT_FIELD_START_X,passwordTextField.getY() + INPUT_FIELD_HEIGHT + 10,INPUT_FIELD_WIDTH,INPUT_FIELD_HEIGHT);
        if(target != null) rePasswordTextField.setText(target.getPassword());
        rootPanel.add(rePasswordTextField);

        okButton = new JButton("确定");
        okButton.setBounds(TEXT_LABEL_START_X + TEXT_LABEL_WIDTH ,152,70,24);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(keywordTextField.getText().length() == 0){
                    Utils.showWaring("提示","关键字不能为空");
                    return;
                }
                if(!(new String(passwordTextField.getPassword())).equals(new String(rePasswordTextField.getPassword()))){
                    Utils.showWaring("提示","两次密码不一致");
                    return;
                }
                if(target == null){ //就是新建
                    target = new PasswordModel();
                    target.setId(PasswordModel.NEW_ITEM);
                    target.setKeyword(keywordTextField.getText());
                    target.setUsername(userNameTextField.getText());
                    target.setPassword(new String(passwordTextField.getPassword()));
                }else{ //修改已有
                    target.setKeyword(keywordTextField.getText());
                    target.setUsername(userNameTextField.getText());
                    target.setPassword(new String(passwordTextField.getPassword()));
                }
                if(dataEventListener != null) dataEventListener.dataChange(target);
                rootFrame.dispose();
            }
        });
        rootPanel.add(okButton);

        cancelButton = new JButton("取消");
        cancelButton.setBounds(okButton.getX() + okButton.getWidth() + 10,152,70,24);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rootFrame.dispose();
            }
        });
        rootPanel.add(cancelButton);

        rootFrame.setVisible(true);
    }

    public void dispose(){
        rootFrame.dispose();
    }

    public void setDataEventListener(DataEventListener listener){
        dataEventListener = listener;
    }

    //数据变更事件监听器接口
    public interface DataEventListener{
        void dataChange(PasswordModel model);
    }
}
