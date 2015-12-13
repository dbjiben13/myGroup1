package Util;

import java.awt.*;

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
}
