package Windows;

import javax.swing.*;

/**
 * 添加/编辑窗口
 * Created by attect on 15-12-13.
 */
public class EditWindow {
    private static final String TEXT_WINDOW_TITLE = "编辑";
    private static final int WINDOW_WIDTH = 300;//窗口宽度
    private static final int WINDOW_HEIGHT = 200; //窗口高度
    private JFrame rootFrame;
    private JPanel rootPanel;

    private JLabel inputKeywordText;            //输入关键字提示文字
    private JLabel inputUserNameText;           //输入用户名提示文字
    private JLabel inputPasswordText;           //输入密码提示文字
    private JLabel reInputPasswordText;         //再次输入密码提示文字

    private JTextField keywordTextField;        //关键字输入框
    private JTextField userNameTextField;       //用户名输入框
    private JPasswordField passwordTextField;   //密码输入框
    private JPasswordField rePasswordTextField; //确认密码输入框


}
