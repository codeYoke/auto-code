package com.fjh.codeAuto;
import java.sql.*;


/**
 * ClassName: DbUtil
 * Description:数据库连接工具类
 * date: 2019/9/2 15:05
 *
 * @author 冯佳豪
 */
public class DbUtil {

        static String url = DbConstant.URL;
        static String user = DbConstant.USERNAME ;
        static String pwd = DbConstant.PASSWORD;
        static String driver = DbConstant.DRIVER;

        static {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public static Connection getCon() {
            try {
                return DriverManager.getConnection(url, user, pwd);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        //关闭数据库连接
        public static void close(Connection connection, Statement statement, ResultSet resultSet){
            try {
                if(null != connection) connection.close();
                if(null != statement) statement.close();
                if(null != resultSet) resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public static void close(Connection connection){
            close(connection,null,null);
        }
        public static void close(Connection connection,Statement statement){
            close(connection,statement,null);
        }
    }


