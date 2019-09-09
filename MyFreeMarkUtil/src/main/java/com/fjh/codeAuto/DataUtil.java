package com.fjh.codeAuto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DataUtil
 * Description:数据库元数据抽取工具类
 * date: 2019/9/3 16:34
 *
 * @author 冯佳豪
 */
public class DataUtil {


    //构建一个数据库的列和实体类的属性的映射map关系
    Map<String,String > columnReProperties = new HashMap<>();
    /**
     * 获取数据库表对应的元数据转换成符合java代码规范格式 eg:stu_name -> stuName等，
     * 并将数据放入DataProperties处理
     * @param tableName 表名
     * @param dataBaseName 数据库名
     * @return List<DataProperties>
     * @throws SQLException
     */
    public List<DataProperties> getMetaData(String tableName,String dataBaseName) throws SQLException {
        List<DataProperties> fields = new ArrayList<>();

        //连接数据库
        Connection con = DbUtil.getCon();
        //获取数据库元数据
        DatabaseMetaData metaData = con.getMetaData();
        /*参数说明：
        参数:catalog:目录名称，一般都为空.
        参数：schema:数据库名，对于oracle来说就用户名
        参数：tableName:表名称
        参数：type :表的类型(TABLE | VIEW)*/
        ResultSet rs = metaData.getColumns(null, dataBaseName, tableName, null);
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        while (rs.next()){
          /*  //注意：元数据下标是从1开始,通过这里可以看到表的元数据
            for (int i = 1; i <= count; i++) {
                String columnClassName = rsMetaData.getColumnName(i);
                Object rsObject = rs.getObject(i);
                System.out.println("columnName:"+columnClassName+"   rsObject:"+rsObject);
            }
            System.out.println("============");*/

            //获取注释
            String remarks = rs.getString("REMARKS");
            //获取数据类型名
            String typeName = rs.getString("TYPE_NAME");
            //获取列名
            String columnName = rs.getString("COLUMN_NAME");

            System.out.println(remarks+":"+typeName+":"+columnName);

            //将列名转化成固定格式：user_name  ->  userName
            String[] columParts = columnName.split("_"); //user  name
            //拼接字符串，将非第一数组的首字母大写
            String newColumnName = "";
            for (int i = 0; i < columParts.length; i++) {
                if(i == 0){
                    newColumnName = columParts[i]+newColumnName;
                }else {
                    newColumnName = newColumnName + columParts[i].substring(0,1).toUpperCase() + columParts[i].substring(1);
                    //stu+""+Name  继续循环-->names[i].substring(1)截取从第二个开始截取子串;
                }
            }

            //构建一个数据库的列和实体类的属性的映射map关系  columnname 是数据库的列名  第二个是  属性名
            columnReProperties.put(columnName,newColumnName);

            //将数据放入dataproperties进行转换，将类型转换为java可读类型
            DataProperties properties =new DataProperties(remarks, typeName, newColumnName);
            fields.add(properties);
        }
        return fields;
    }

    public  Map<String,String > getColumnReProperties(){

       return columnReProperties;
    }
}

