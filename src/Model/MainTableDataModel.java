package Model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * 列表数据模型
 * Created by attect on 15-12-13.
 */
public class MainTableDataModel extends AbstractTableModel {
    private static final String[] columnName = {"编号", "关键字", "用户名", "密码"};
    private ArrayList<PasswordModel> passwordModel;

    public MainTableDataModel(ArrayList<PasswordModel> model){
        passwordModel = model;
    }

    public String getColumnName(int column) {
        return columnName[column];
    }

    public int getColumnCount() {
        return columnName.length;
    }

    public int getRowCount() {
        return passwordModel.size();
    }

    public Object getValueAt(int row, int col) {
        switch (col){
            case 0://编号,id
                return passwordModel.get(row).getId();
            case 1://关键字,keyword
                return passwordModel.get(row).getKeyword();
            case 2://用户名,username
                return passwordModel.get(row).getUsername();
            case 3://密码,password
                return passwordModel.get(row).getPassword();
            default:
                return "";
        }
    }

}
