import Manager.DBManager;
import Manager.SoftwarePasswordManager;
import Util.Utils;
import Windows.LoginWindow;
import Windows.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            DBManager dbManager = new DBManager(); //提前创建一次数据库?
        } catch (DBManager.DBManagerException e) {
            e.printStackTrace();
        }
        final LoginWindow loginWindow = new LoginWindow();
        loginWindow.setLoginEventListener(new LoginWindow.LoginEventListener() {
            @Override
            public void onClose() {
                //System.out.println("关闭了登录窗口");
            }

            @Override
            public void onCorrect() {
                //System.out.println("密码正确");
                loginWindow.dispose();
                initMainWindow();
            }

            @Override
            public void onWrong(String message) {
                JOptionPane.showConfirmDialog(null,
                        message, "错误", JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, null);
                //System.out.println("错误：" + message);
            }

            @Override
            public void onNewUser() {
                //System.out.println("新用户");
            }
        });
        loginWindow.init();//显示窗口

    }

    public static void initMainWindow(){
        MainWindow mainWindow = new MainWindow();
        mainWindow.init();//显示主窗口
    }
}
