package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName: GenEntity
 * Description:自动生成实体类java文件
 * date: 2019/9/3 17:22
 *
 * @author 冯佳豪
 */
public class GenEntity {
    //外部定义一个工具类，并提供，目的使得生成dao是获取的属性参数能够一致
    DataUtil dataUtil = new DataUtil();
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

    public DataUtil getDataUtil() {
        return dataUtil;
    }
}
