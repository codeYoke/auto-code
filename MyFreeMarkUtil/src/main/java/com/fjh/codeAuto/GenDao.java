package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: GenDao
 * Description:
 * date: 2019/9/4 12:26
 *
 * @author 冯佳豪
 */
public class GenDao {
    public void generateDaoFile(String tableName, String packageName,String basePath,String entityPackageName,DataUtil dataUtil) throws SQLException {
        //构建一个数据库的列和实体类的属性的映射map关系
        Map<String,String > columnReProperties = new HashMap<>();
        //DataUtil dataUtil = new DataUtil();//不能够new,会使columnReProperties不是同一个对象
        columnReProperties = dataUtil.getColumnReProperties();

        //存放路径
        String path = basePath + packageName.replaceAll("\\.", "/") + "/";
        System.out.println(path);
        File FilePath = new File(path);
        if (!FilePath.exists()) {
            FilePath.mkdirs();//创建目录
        }
        //根据表名创建实体类名称，按照一定格式  :auto_code  ->  AutoCodeDao
        String[] tableParts = tableName.split("_"); //auto  code
        //拼接字符串，将非第一数组的首字母大写
        String newTableName = "";
        for (int i = 0; i < tableParts.length; i++) {
            newTableName = newTableName + tableParts[i].substring(0, 1).toUpperCase() + tableParts[i].substring(1);
        }
         String daoClassName = newTableName+"Dao";

        //使用模板生成相对应的类
        Map rootData = new HashMap();
        rootData.put("daoPackageName", packageName);
        rootData.put("daoClassName", daoClassName);


        String entityType = entityPackageName + "."+newTableName;  //AutoCode  > autoCode ${entityType} ${entityParamName?uncap_first}
        String entityParamName = newTableName;
        rootData.put("entityType",entityType);
        rootData.put("entityParamName",entityParamName);
        //sql中的表名
        rootData.put("tableName",tableName);
        //列名和属性名的对应关系
        rootData.put("columnReProperties",columnReProperties);
        rootData.put("date",new Date());

        FreeMarkerUtils.printFile("dao.ftl", rootData, path + daoClassName + ".java");
    }
}
