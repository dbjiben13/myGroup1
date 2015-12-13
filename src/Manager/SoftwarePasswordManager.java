package Manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 软件密码管理器
 * Created by Attect on 2015/12/12 0012.
 */
public class SoftwarePasswordManager {
    private static final String fileName = "spm.data";
    private static SoftwarePasswordManager myInstance;
    private static File passwordFile;
    private static Boolean newUser = false;

    private SoftwarePasswordManager(){
        if(passwordFile == null) passwordFile = new File(fileName);
        if(passwordFile.length() < 1 || !passwordFile.exists()) newUser = true; //是否新用户
    }

    public static SoftwarePasswordManager getInstance(){
        if(myInstance == null){
            myInstance = new SoftwarePasswordManager();
        }
        return myInstance;
    }

    public Boolean isNewUser() {
        return newUser;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public boolean setPassword(String password) throws SoftwarePasswordException{
        if(password.length() <1 || password.length() > 16) return false; //不符合长度的密码直接认为错误
        if(!passwordFile.exists()){
            try {
                if (!passwordFile.createNewFile()) {
                    throw new SoftwarePasswordException("密码文件创建失败"); //抛出文件创建异常
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(passwordFile);
            outputStream.write(password.getBytes());
            outputStream.close();
            newUser = false; //密码设置成功后就不是新用户了
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            if(outputStream != null){
                try{
                    outputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 密码正确判断
     * @param password 用户输入密码
     * @return 密码是否正确
     */
    public boolean checkPassword(String password){
        if(password.length() <1 || password.length() > 16) return false; //不符合长度的密码直接认为错误
        if(!passwordFile.exists()) return false; //如果文件不存在则直接失败

        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream(passwordFile);
            byte[] buf = new byte[100]; //读取文件
            int len = inputStream.read(buf);
            String savePassword = new String(buf,0,len);
            return password.equals(savePassword); //判断密码是否正确
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public class SoftwarePasswordException extends  Exception{
        private String message;
        public SoftwarePasswordException(String message) {
            super(message);
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
