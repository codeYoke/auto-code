package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.awt.image.RasterOp;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GenPage
 * Description:
 * date: 2019/9/5 10:43
 *
 * @author 冯佳豪
 */
public class GenPage {
    public void generatePageFile(String pagePath,String tableName,String dataBaseName, DataUtil dataUtil) throws SQLException {

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

        //使用模板生成相对应的类
        Map rootData = new HashMap();
        rootData.put("servletClassName", servletClassName);
        rootData.put("pageName",pageName);
        rootData.put("fields",fields);

        FreeMarkerUtils.printFile("page.ftl", rootData, pagePath+"/" + pageName + ".html");
    }
}
