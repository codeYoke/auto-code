package com.fjh.codeAuto;

import java.sql.SQLException;

/**
 * ClassName: TestAuto
 * Description:
 * date: 2019/9/5 15:17
 *
 * @author 冯佳豪
 */
public class TestAuto {
    public static void main(String[] args) throws SQLException {

        String tableName = "auto_code";
        String dataBaseName = "freemarkdemo";
        String basePath ="src/main/java/";

        GenerateFile generateFile = new GenerateFile();


        //生成entitys类
        String entitytPackageName = "com.fjh.entity";

        generateFile.genrateEntityFile(tableName,dataBaseName,entitytPackageName,basePath);

        //生成dao类
        String daoPackageName = "com.fjh.dao";

        generateFile.generateDaoFile(tableName,daoPackageName,basePath,entitytPackageName);

        //生成servlet
        String servletPackageName = "com.fjh.servlet";

        generateFile.generateServletFile(tableName,servletPackageName,basePath,entitytPackageName,daoPackageName);

        //生成html前台页面
        String pagePath = "src/main/webapp";

        generateFile.generatePageFile(pagePath,tableName,dataBaseName);

    }
}
