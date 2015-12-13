package Windows;

import Util.Utils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主窗口
 * Created by Attect on 2015/12/12 0012.
 */
public class MainWindow {
    private static final String TEXT_WINDOW_TITLE = "密码管理";
    private static final int WINDOW_WIDTH = 410;//窗口宽度
    private static final int WINDOW_HEIGHT = 300; //窗口高度
    private JFrame rootFrame;
    private JPanel rootPanel;

    private JTextField keywordTextFiled;
    private JButton copyUserNameButton;
    private JButton copyPasswordButton;
    private JScrollPane listScrollPane;
    private JTable listTable;
    private JButton addButton,editButton,deleteButton,closeButton;


    TableModel dataModel = new AbstractTableModel() {
        String[] columnName = {"编号","关键字","用户名","密码"};
        public String getColumnName(int column){return  columnName[column];}

        public int getColumnCount() { return 4; }
        public int getRowCount() { return 10;}
        public Object getValueAt(int row, int col) { return new Integer(row*col); }
    };

    public void init(){
        rootFrame = new JFrame(TEXT_WINDOW_TITLE);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rootFrame.setBounds(Utils.centerWindowStartX(WINDOW_WIDTH), Utils.centerWindowStartY(WINDOW_HEIGHT),WINDOW_WIDTH,WINDOW_HEIGHT);

        rootPanel = new JPanel(null);
        rootFrame.setContentPane(rootPanel);

        //关键字输入框
        keywordTextFiled = new JTextField();
        keywordTextFiled.setBounds(10,10,200,24);
        rootPanel.add(keywordTextFiled);

        //复制用户名按钮
        copyUserNameButton = new JButton("用户名");
        copyUserNameButton.setBounds(220,10,90,24);
        rootPanel.add(copyUserNameButton);

        //复制密码按钮
        copyPasswordButton = new JButton("密码");
        copyPasswordButton.setBounds(320,10,70,24);
        rootPanel.add(copyPasswordButton);

        //主要列表
        listTable = new JTable(dataModel);
        listScrollPane = new JScrollPane(listTable);
        listScrollPane.setBounds(10, 44, 380, 178);
        rootPanel.add(listScrollPane);

        //添加按钮
        addButton = new JButton("添加");
        addButton.setBounds(10,233,70,24);
        rootPanel.add(addButton);

        //编辑按钮
        editButton = new JButton("编辑");
        editButton.setBounds(90,233,70,24);
        rootPanel.add(editButton);

        //删除按钮
        deleteButton = new JButton("删除");
        deleteButton.setBounds(170,233,70,24);
        rootPanel.add(deleteButton);

        //关闭按钮
        closeButton = new JButton("关闭");
        closeButton.setBounds(250,233,139,24);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        rootPanel.add(closeButton);

        rootFrame.setVisible(true);
    }
}
