package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GenController
 * Description:
 * date: 2019/9/4 16:58
 *
 * @author 冯佳豪
 */
public class GenController {

    //需要实体类名，包名；dao类名，包名，自身类名，包名
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
}
