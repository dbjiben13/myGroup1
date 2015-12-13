package Windows;

import Util.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口
 * Created by Attect on 2015/12/12 0012.
 */
public class MainWindow {
    private static final String TEXT_WINDOW_TITLE = "密码管理";
    private static final int WINDOW_WIDTH = 400;//窗口宽度
    private static final int WINDOW_HEIGHT = 300; //窗口高度
    private JFrame rootFrame;
    private JPanel rootPanel;

    public void init(){
        rootFrame = new JFrame(TEXT_WINDOW_TITLE);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rootFrame.setBounds(Utils.centerWindowStartX(WINDOW_WIDTH), Utils.centerWindowStartY(WINDOW_HEIGHT),WINDOW_WIDTH,WINDOW_HEIGHT);

        rootPanel = new JPanel(new FlowLayout());
        rootFrame.setContentPane(rootPanel);

        rootFrame.setVisible(true);
    }
}
