package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: GenerateFile
 * Description:自动生成文件类
 * date: 2019/9/5 15:05
 *
 * @author 冯佳豪
 */
public class GenerateFile {

    DataUtil dataUtil = new DataUtil();

    /**
     *
     * @param tableName 数据库表名
     * @param dataBaseName 数据库名
     * @param packageName 文件包名
     * @param basePath 项目基础路径（包之前）
     * @throws SQLException
     */
    public void genrateEntityFile(String tableName, String dataBaseName,String packageName,String basePath) throws SQLException {
        //用来接收转换后的参数
        List<DataProperties> fields = new ArrayList<>();

        fields = dataUtil.getMetaData(tableName, dataBaseName);
        //利用编写好的模板生成一个实体类

        //存放路径
        String path = basePath + packageName.replaceAll("\\.", "/") + "/";
        System.out.println(path);
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();//创建目录
        }
        //根据表名创建实体类名称，按照一定格式  :auto_code  ->  AutoCode
        String[] tableParts = tableName.split("_"); //auto  code
        //拼接字符串，将非第一数组的首字母大写
        String newTableName = "";
        for (int i = 0; i < tableParts.length; i++) {
            newTableName = newTableName + tableParts[i].substring(0, 1).toUpperCase() + tableParts[i].substring(1);
        }

        //使用模板生成相对应的类
        Map rootData = new HashMap();
        rootData.put("entityPackageName", packageName);
        rootData.put("entityClassName", newTableName);
        rootData.put("fields", fields);//属性的数据
        rootData.put("date",new Date());

        FreeMarkerUtils.printFile("entity.ftl", rootData, path + newTableName + ".java");


    }

    /**
     *
     * @param tableName 数据库表名
     * @param packageName 文件包名
     * @param basePath 项目基础路径（包之前）
     * @param entityPackageName entity类的包名
     * @throws SQLException
     */
    public void generateDaoFile(String tableName, String packageName,String basePath,String entityPackageName) throws SQLException {
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

        //在将数据存放map中，在模板文件中获取
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

    /**
     * 根据数据库表自动生成servlet页面
     * @param tableName 数据库表名
     * @param packageName 文件包名
     * @param basePath 项目基础路径（包之前）
     * @param entityPackageName entity类的包名
     * @param daoPackageName dao类包名
     */
    public void generateServletFile(String tableName, String packageName,String basePath,String entityPackageName,String daoPackageName){
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
        String servletClassName = newTableName+"Servlet";

        //使用模板生成相对应的类
        Map rootData = new HashMap();
        rootData.put("controllerPackageName", packageName);
        rootData.put("controllerClassName", servletClassName);


        String entityType = entityPackageName + "."+newTableName;  //AutoCode  > autoCode ${entityType} ${entityParamName?uncap_first}
        String entityClassName = newTableName;
        rootData.put("entityType",entityType);
        rootData.put("entityClassName",entityClassName);

        String daoClassName = newTableName+"Dao";
        String daoType = daoPackageName + "."+daoClassName;  //AutoCode  > autoCode ${entityType} ${entityParamName?uncap_first}
        rootData.put("daoClassName",daoClassName);
        rootData.put("daoType",daoType);

        //sql中的表名
        rootData.put("tableName",tableName);

        rootData.put("date",new Date());

        FreeMarkerUtils.printFile("servlet.ftl", rootData, path + servletClassName + ".java");
    }

    /**
     * 根据数据库表自动生成简单html页面
     * @param pagePath 生成页面路径
     * @param tableName 数据库表名
     * @param dataBaseName 数据库名
     * @throws SQLException
     */
    public void generatePageFile(String pagePath,String tableName,String dataBaseName) throws SQLException {

        //用来接收转换后的参数
        List<DataProperties> fields = new ArrayList<>();
        //获取元数据，注释remark作为表单前面的名称，在模板文件中取${field.remark}
        fields = dataUtil.getMetaData(tableName, dataBaseName);

        File pageFilepath = new File(pagePath);
        if(!pageFilepath.exists()){
            pageFilepath.mkdirs();
        }

        //根据表名创相对应的页面名称，页面名与表名一致
        String pageName = tableName;

        //根据表名对应的servlet名称，对应post的action  eg:auto_code->AutoCodeServlet
        String[] tableParts = tableName.split("_"); //auto  code
        //拼接字符串，将非第一数组的首字母大写
        String newTableName = "";
        for (int i = 0; i < tableParts.length; i++) {
            newTableName = newTableName + tableParts[i].substring(0, 1).toUpperCase() + tableParts[i].substring(1);
        }
        String servletClassName = newTableName+"Servlet";

        //在将数据存放map中，在模板文件中获取
        Map rootData = new HashMap();
        rootData.put("servletClassName", servletClassName);
        rootData.put("pageName",pageName);
        rootData.put("fields",fields);

        FreeMarkerUtils.printFile("page.ftl", rootData, pagePath+"/" + pageName + ".html");
    }
    public DataUtil getDataUtil() {
        return dataUtil;
    }
}
