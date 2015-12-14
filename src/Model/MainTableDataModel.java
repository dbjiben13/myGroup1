package Model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * 列表数据模型
 * Created by attect on 15-12-13.
 */
public class MainTableDataModel extends AbstractTableModel {
    private static final String[] columnName = {"编号", "关键字", "用户名", "密码"};
    private ArrayList<PasswordModel> passwordModels;

    public MainTableDataModel(ArrayList<PasswordModel> model){
        passwordModels = model;
    }

    public String getColumnName(int column) {
        return columnName[column];
    }

    public int getColumnCount() {
        return columnName.length;
    }

    public int getRowCount() {
        return passwordModels.size();
    }

    public Object getValueAt(int row, int col) { //row行 col列
        switch (col){
            case 0://编号,id
                return passwordModels.get(row).getId();
            case 1://关键字,keyword
                return passwordModels.get(row).getKeyword();
            case 2://用户名,username
                return passwordModels.get(row).getUsername();
            case 3://密码,password
                return passwordModels.get(row).getPassword();
            default:
                return "";
        }
    }

}
