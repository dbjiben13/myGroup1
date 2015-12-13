package Manager;

import Model.PasswordModel;
import org.sqlite.JDBC;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * 数据库
 * Created by attect on 15-12-13.
 */
public class DBManager {
    public static final int DB_ERROR_EXIT = 2374;
    private static final String DB_ADDRESS = "jdbc:sqlite:data.db";
    private static final int QUERY_FAILED = -1;
    private static final String TABLE_NAME = "T_PASSWORD";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(pid       INTEGER PRIMARY KEY," +     //PID 自增主键
            " keyword   TEXT    NOT NULL, " +       //关键字
            " username  INT     NOT NULL, " +       //用户名
            " password  CHAR(50))";                 //密码
    private static final String SQL_ADD_PASSWORD_RECORD = "INSERT INTO " + TABLE_NAME + "(keyword,username,password) VALUES (?,?,?)";    //增
    private static final String SQL_DELETE_PASSWORD_RECORD = "DELETE FROM " +  TABLE_NAME + " WHERE pid=?";                              //删
    private static final String SQL_UPDATE_PASSWORD_RECORD = "UPDATE " + TABLE_NAME + " SET keyword=?,username=?,password=? WHERE pid=?";//改
    private static final String SQL_SELECT_ALL_PASSWORD_RECORD = "SELECT pid,keyword,username,password FROM " + TABLE_NAME;              //查
    private static final String SQL_SELECT_PASSWORD_RECORD_BY_KEYWORD = "SELECT pid,keyword,username,password FROM " + TABLE_NAME + " WHERE keyword like \'%\'||?||\'%\'";//关键字查

    public DBManager() throws DBManagerException{
        File dbFile = new File("data.db");
        if(!dbFile.exists()){
            createTable();
        }
    }

    /**
     * 增加一个记录
     * @param model 数据
     * @return 添加的行数
     * @throws DBManagerException
     */
    public int addPasswordRecord(PasswordModel model) throws DBManagerException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.prepareStatement(SQL_ADD_PASSWORD_RECORD); //参数化查询
            statement.setString(1,model.getKeyword());     //关键字
            statement.setString(2,model.getUsername());    //用户名
            statement.setString(3,model.getPassword());    //密码
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBManagerException("新增数据时发生异常");
        }finally {
            if(statement!=null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除记录
     * @param  model PasswordModel
     * @return 删除行数
     * @throws DBManagerException
     */
    public int deletePasswordRecord(PasswordModel model) throws DBManagerException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.prepareStatement(SQL_DELETE_PASSWORD_RECORD); //参数化查询
            statement.setInt(1, model.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBManagerException("删除时发生异常");
        }finally {
            if(statement!=null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 修改记录
     * @param model PasswordModel
     * @return 修改行数
     * @throws DBManagerException
     */
    public int updatePasswordRecord(PasswordModel model) throws DBManagerException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.prepareStatement(SQL_UPDATE_PASSWORD_RECORD); //参数化查询
            statement.setString(1,model.getKeyword());
            statement.setString(2,model.getUsername());
            statement.setString(3,model.getPassword());
            statement.setInt(4, model.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBManagerException("修改时发生异常");
        }finally {
            if(statement!=null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询所有记录
     * @return 结果集
     * @throws DBManagerException
     */
    public ArrayList<PasswordModel> selectPasswordRecord() throws DBManagerException{
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PASSWORD_RECORD);
            return buildModelWithResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBManagerException("查询全部记录时发生异常");
        }finally {
            if(statement!=null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据关键字查找记录
     * @param keyword
     * @return
     * @throws DBManagerException
     */
    public ArrayList<PasswordModel> selectPasswordRecord(String keyword) throws DBManagerException{
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.prepareStatement(SQL_SELECT_PASSWORD_RECORD_BY_KEYWORD); //参数化查询
            statement.setString(1, keyword);
            ResultSet resultSet = statement.executeQuery();
            return buildModelWithResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBManagerException("根据关键字查询时发生异常");
        }finally {
            if(statement!=null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据ResultSet创建PasswordModel数组
     * @param resultSet
     * @return
     */
    private ArrayList<PasswordModel> buildModelWithResultSet(ResultSet resultSet){
        ArrayList<PasswordModel> passwordModel = new ArrayList<PasswordModel>();
        try{
            while (resultSet.next()){
                PasswordModel model = new PasswordModel();
                model.setId(resultSet.getInt("pid"));
                model.setKeyword(resultSet.getString("keyword"));
                model.setUsername(resultSet.getString("username"));
                model.setPassword(resultSet.getString("password"));
                //System.out.println("pid:" + model.getId() + " keyword:" + model.getKeyword() + " username:" + model.getUsername() + " password:" + model.getPassword());
                passwordModel.add(model);
            }
            return passwordModel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwordModel;

    }

    /**
     * 创建所需要的表
     */
    private void createTable() throws DBManagerException{
        execute(CREATE_TABLE);
    }

    /**
     * 执行SQL语句
     * @param sql
     * @return
     * @throws DBManagerException
     */
    public int execute(String sql) throws DBManagerException{
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
            throw new DBManagerException("数据库操作出错");
        }finally {
            if(statement != null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 执行SQL语句查询
     * @param sql
     * @return
     * @throws DBManagerException
     */
    public ResultSet query(String sql) throws DBManagerException{
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(DB_ADDRESS);
            statement = connection.createStatement();
            return statement.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
            throw new DBManagerException("数据库操作出错");
        }finally {
            if(statement != null){
                try{
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class DBManagerException extends Exception{
        @Override
        public String getMessage() {
            return message;
        }

        String message = "";
        DBManagerException(String s){
            message = s;
        }
    }
}
