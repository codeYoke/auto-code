package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAutoCode {
    public static void main(String[] args) throws SQLException {

        String tableName = "auto_code";
        String dataBaseName = "freemarkdemo";
        String basePath ="src/main/java/";

        //生成entitys类
        String entitytPackageName = "com.fjh.entity";
        GenEntity genEntity = new GenEntity();
        genEntity.genrateEntityFile(tableName,dataBaseName,entitytPackageName,basePath);


        //生成dao类
        String daoPackageName = "com.fjh.dao";
        GenDao genDao = new GenDao();
        genDao.generateDaoFile(tableName,daoPackageName,basePath,entitytPackageName,genEntity.getDataUtil());

        //生成servlet
        String servletPackageName = "com.fjh.servlet";
        GenController genController = new GenController();
        genController.generateServletFile(tableName,servletPackageName,basePath,entitytPackageName,daoPackageName);

    }
}

