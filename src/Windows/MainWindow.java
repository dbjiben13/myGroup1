package Windows;

import Manager.DBManager;
import Model.MainTableDataModel;
import Model.PasswordModel;
import Util.Utils;
import Windows.EditWindow.DataEventListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

    private int currentTableSelect = 0; //列表选中行
    private ArrayList<PasswordModel> currentListContent = new ArrayList<PasswordModel>();


    public void init(){
        rootFrame = new JFrame(TEXT_WINDOW_TITLE);
        rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rootFrame.setBounds(Utils.centerWindowStartX(WINDOW_WIDTH), Utils.centerWindowStartY(WINDOW_HEIGHT),WINDOW_WIDTH,WINDOW_HEIGHT);

        rootPanel = new JPanel(null);
        rootFrame.setContentPane(rootPanel);

        //关键字输入框
        keywordTextFiled = new JTextField();
        keywordTextFiled.setBounds(10,10,200,24);
        keywordTextFiled.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                refreshTableWithKeyword();
            }
        });
        rootPanel.add(keywordTextFiled);

        //复制用户名按钮
        copyUserNameButton = new JButton("用户名");
        copyUserNameButton.setBounds(220,10,90,24);
        copyUserNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currentListContent.size() > 0){ //有数据才进行操作
                    Utils.copyStringToClipboard(currentListContent.get(currentTableSelect).getUsername());
                }
            }
        });
        rootPanel.add(copyUserNameButton);

        //复制密码按钮
        copyPasswordButton = new JButton("密码");
        copyPasswordButton.setBounds(320,10,70,24);
        copyPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currentListContent.size() > 0){ //有数据才进行操作
                    Utils.copyStringToClipboard(currentListContent.get(currentTableSelect).getPassword());
                }
            }
        });
        rootPanel.add(copyPasswordButton);


        //获得一次数据
        ArrayList<PasswordModel> passwordModels = new ArrayList<PasswordModel>();
        try {
            DBManager dbManager = new DBManager();
            passwordModels = dbManager.selectPasswordRecord();
            currentListContent = passwordModels;
        } catch (DBManager.DBManagerException e) {
            Utils.showWaring("错误","数据库发生错误");
            e.printStackTrace();
            System.exit(DBManager.DB_ERROR_EXIT);
        }

        //主要列表
        listTable = new JTable(new MainTableDataModel(passwordModels));
        ListSelectionModel listSelectionModel = listTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if(listSelectionEvent.getValueIsAdjusting()){//鼠标按下立即响应
                    currentTableSelect = listTable.getSelectedRow();
                    if(currentTableSelect < 0) currentTableSelect = 0;
                }
            }
        });
        listScrollPane = new JScrollPane(listTable);
        listScrollPane.setBounds(10, 44, 380, 178);
        rootPanel.add(listScrollPane);

        //添加按钮
        addButton = new JButton("添加");
        addButton.setBounds(10,233,70,24);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                EditWindow editWindow = new EditWindow(); //编辑窗口
                editWindow.init();
                editWindow.setDataEventListener(new AddNewItemListener());
            }
        });
        rootPanel.add(addButton);

        //编辑按钮
        editButton = new JButton("编辑");
        editButton.setBounds(90,233,70,24);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    EditWindow editWindow = new EditWindow(currentListContent.get(currentTableSelect));
                    editWindow.init();
                    editWindow.setDataEventListener(new EditItemListener());
                }catch (ArrayIndexOutOfBoundsException e){
                    Utils.showMessage("提示","请选中一行");
                }

            }
        });
        rootPanel.add(editButton);

        //删除按钮
        deleteButton = new JButton("删除");
        deleteButton.setBounds(170,233,70,24);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteTableItem();
            }
        });
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

    private ArrayList<PasswordModel> getTableContentWithKeyword(String keyword){
        ArrayList<PasswordModel> passwordModels = new ArrayList<PasswordModel>();
        try {
            DBManager dbManager = new DBManager();
            passwordModels = dbManager.selectPasswordRecord(keyword);
        } catch (DBManager.DBManagerException e) {
            Utils.showWaring("错误","数据库发生错误");
            e.printStackTrace();
            System.exit(DBManager.DB_ERROR_EXIT);
        }
        return passwordModels;
    }

    private ArrayList<PasswordModel> getAllTableContent(){
        ArrayList<PasswordModel> passwordModels = new ArrayList<PasswordModel>();
        try {
            DBManager dbManager = new DBManager();
            passwordModels = dbManager.selectPasswordRecord();
        } catch (DBManager.DBManagerException e) {
            Utils.showWaring("错误","数据库发生错误");
            e.printStackTrace();
            System.exit(DBManager.DB_ERROR_EXIT);
        }
        return passwordModels;
    }

    private void refreshTableWithKeyword(){
        if(keywordTextFiled.getText().length() > 0){
            refreshTableWithData(getTableContentWithKeyword(keywordTextFiled.getText()));
        }else {
            refreshTableWithData(getAllTableContent());
        }
    }

    private void deleteTableItem(){
        if(currentListContent.size() > 0 && currentListContent.size() > currentTableSelect){ //如果列表里有内容且选择的项目是有效计数的
            try {
                DBManager dbManager = new DBManager();
                dbManager.deletePasswordRecord(currentListContent.get(currentTableSelect));
            } catch (DBManager.DBManagerException e) {
                e.printStackTrace();
                Utils.showWaring("错误",e.getMessage());
            }
        }
        refreshTableWithKeyword();
    }

    private void refreshTableWithData(ArrayList<PasswordModel> models){
        currentTableSelect = 0; //重新默认选择第0行
        currentListContent = models;//设置最新的数据
        listTable.setModel(new MainTableDataModel(models));
        System.gc();
    }

    public class AddNewItemListener implements DataEventListener{

        @Override
        public void dataChange(PasswordModel model) {
            try {
                DBManager dbManager = new DBManager();
                if(dbManager.addPasswordRecord(model) > 0){
                    Utils.showMessage("信息","操作成功");
                    refreshTableWithData(getAllTableContent());
                }else{
                    Utils.showWaring("信息","操作失败");
                }
            } catch (DBManager.DBManagerException e) {
                e.printStackTrace();
                Utils.showWaring("失败",e.getMessage());
            }

        }
    }

    public class EditItemListener implements DataEventListener{

        @Override
        public void dataChange(PasswordModel model) {
            try {
                DBManager dbManager = new DBManager();
                if(dbManager.updatePasswordRecord(model) > 0){
                    refreshTableWithKeyword();
                }
            } catch (DBManager.DBManagerException e) {
                e.printStackTrace();
                Utils.showWaring("信息","操作失败");
            }

        }
    }
}
