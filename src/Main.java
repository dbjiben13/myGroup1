import Manager.DBManager;
import Manager.SoftwarePasswordManager;
import Util.Utils;
import Windows.LoginWindow;
import Windows.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //System.out.println("width:" + Utils.getScreenDimension().width + " height:" + Utils.getScreenDimension().height + " isNewUser:" + SoftwarePasswordManager.getInstance().isNewUser());
//        try{
//            SoftwarePasswordManager.getInstance().setPassword("abcdefg");
//        }catch (SoftwarePasswordManager.SoftwarePasswordException e){
//            e.printStackTrace();
//        }
//        System.out.println("密码结果:" + SoftwarePasswordManager.getInstance().checkPassword("abcdefg"));
        try {
            DBManager dbManager = new DBManager();
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
        loginWindow.init();
//        String sql = "CREATE TABLE COMPANY " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";
//        DBManager dbManager = new DBManager();
//        dbManager.test(sql);
    }

    public static void initMainWindow(){
        MainWindow mainWindow = new MainWindow();
        mainWindow.init();
    }
}
