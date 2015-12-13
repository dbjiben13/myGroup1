package Util;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * 工具类
 * Created by Attect on 2015/12/12 0012.
 */
public class Utils {

    private static Dimension screenDimension = null;
    /**
     * 获取屏幕分辨率信息
     * @return Dimension信息，width宽度，height高度
     */
    public static Dimension getScreenDimension(){
        if(screenDimension == null) screenDimension = Toolkit.getDefaultToolkit().getScreenSize(); //如果后续外接显示器，则可能发生分辨率不正确
        return screenDimension;
    }

    public static int centerWindowStartX(int windowsWidth){
        return getScreenDimension().width / 2 - (windowsWidth /2);
    }

    public static int centerWindowStartY(int widowsHeight){
        return getScreenDimension().height /2 - (widowsHeight /2);
    }

    public static void showWaring(String title,String message){
        JOptionPane.showConfirmDialog(null,message, title, JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE, null);
    }

    public static void showMessage(String title,String message){
        JOptionPane.showConfirmDialog(null,message, title, JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null);
    }

    /**
     * 复制字符串到记事本
     * @param s 需要复制的字符串
     */
    public static void copyStringToClipboard(String s){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection stringSel = new StringSelection(s);
        clipboard.setContents(stringSel, null);
    }

}
