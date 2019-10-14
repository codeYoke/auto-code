# auto-code

基于FreeMarker模板引擎实现自动代码生成工具，可以根据数据库表自动生成entity，dao，servlet和简单的表单页面。项目地址：[https://github.com/codeYoke/auto-code](https://github.com/codeYoke/auto-code)

### 前言

因感概业务代码存在大量的增删改查功能，只是针对不同的表数据而已。故想有没有办法，在重复的相同代码中替换某些内容，于是按此思路搜寻，了解到有freemarker这个东西，一番学习后，特记录于此。

### Freemarker简介

FreeMarker是一款模板引擎： 一种基于模板和要改变的数据，并用来生成输出文本（HTML网页、电子邮件、配置文件、源代码等）的通用工具。即：输出=模板+数据。简单来说，其用法原理类似String的replace方法，或MessageFormat的format方法，都是在一定的代码中改变（替换）某些内容。不过FreeMarker更加强大，模板来源可以是外部文件或字符串格式，替换的数据格式多样，而且支持逻辑判断，如此替换的内容将更加灵活。



### 项目主要工具类

![](https://github.com/codeYoke/my-picture/blob/master/auto-code/17571119626891.png?raw=true)

### example

#### 方法1：

我们生成entity和dao只要这样做 ，想要生成其他类做法相同。

```java
public class TestAutoCode {
    public static void main(String[] args) throws SQLException {

        String tableName = "auto_code";//需要生成的表名
        String dataBaseName = "freemarkdemo";//数据库名
        String basePath ="src/main/java/";//基础路径

        //生成entitys
        String entitytPackageName = "com.fjh.entity";
        GenEntity genEntity = new GenEntity();
        genEntity.genrateEntityFile(tableName,dataBaseName,entitytPackageName,basePath);
      	
	//生成dao
       	String daoPackageName = "com.fjh.dao";
 	generateFile.generateDaoFile(tableName,daoPackageName,basePath,entitytPackageName);
	//.....
    }
```

#### 展示结果

![](https://github.com/codeYoke/my-picture/blob/master/auto-code/17001014074918.png)

#### 方法2：

当然你也可以运行web服务，浏览器输入localhost:8080/auto_code,填写表单，这里我们不生成文件，但把代码输出到了页面，方便预览。

![](https://github.com/codeYoke/my-picture/blob/master/auto-code/autocode.png)

#### 展示结果

![](https://github.com/codeYoke/my-picture/blob/master/auto-code/autocode2.png)

![](https://github.com/codeYoke/my-picture/blob/master/auto-code/autocode3.png)

### 核心代码展示

**FreeMarker工具类，用来控制模板文件的输出**

```java
package com.fjh.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class FreeMarkerUtils {
    /*不管一个系统有多少独立的组件来使用 FreeMarker，
     它们都会使用他们自己私有的 Configuration 实例。
     不需要重复创建 Configuration 实例；
      它的代价很高，尤其是会丢失缓存。
      Configuration 实例就是应用级别的单例。*/
    static Configuration cfg = null;
    static {
        //初始化模板引擎
        cfg = new Configuration(Configuration.VERSION_2_3_29);
        //指定模板文件存放的地方
        cfg.setClassLoaderForTemplateLoading(FreeMarkerUtils.class.getClassLoader(), "templates");
        //设置字符编码集
        cfg.setDefaultEncoding("UTF-8");
        //设置异常的处理方式
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //设置输出时间格式
        cfg.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // 获取模板对象
    public static Template getTemplate(String templateName){
        try {
            return  cfg.getTemplate(templateName);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 提供模板文件，输出到控制台
     * @param templateName 模板对象
     * @param dataModel 数据模型
     */
    public static void printConsole(String templateName, Map dataModel){
        Template template = getTemplate(templateName);
        try {
            template.process(dataModel, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提供模板文件名称，输出到文件中
     * @param templateName 模板对象
     * @param dataModel 数据模型
     * @param destFile 目标文件地址
     */
    public static void printFile(String templateName, Map dataModel,String destFile){
        Template template = getTemplate(templateName);
        try {
            template.process(dataModel, new FileWriter(new File(destFile)));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

```

**DataProperties数据库类型转化类**

```java
package com.fjh.codeAuto;

public class DataProperties {

    private String remarks;//数据库字段注释，会在生成文件实体类注释上
    private String typeName;//数据类型名
    private String columnName;//列名

    public DataProperties(String remarks, String typeName, String columnName) {
        this.remarks = remarks;
        this.typeName = typeName;
        this.columnName = columnName;
    }

    public DataProperties() {
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTypeName() {
        //替换成java的数据类型
        if(FieldTypeEnum.INT.name().equals(typeName) ){
            return FieldTypeEnum.INT.getFieldType();
        } else if (FieldTypeEnum.VARCHAR.name().equals(typeName)){
            return FieldTypeEnum.VARCHAR.getFieldType();
        }else if (FieldTypeEnum.FLOAT.name().equals(typeName)){
            return FieldTypeEnum.FLOAT.getFieldType();
        }else if (FieldTypeEnum.TIMESTAMP.name().equals(typeName)){
            return FieldTypeEnum.TIMESTAMP.getFieldType();
        }

        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}

```

**数据库字段类型转java数据类型**

```java
package com.fjh.codeAuto;

public enum FieldTypeEnum {
    INT{
        public  String getFieldType(){
            return "java.lang.Integer";
        }
    },VARCHAR{
        public  String getFieldType(){
            return "java.lang.String";
        }
    },FLOAT{
        public  String getFieldType(){
            return "java.lang.Float";
        }
    },TIMESTAMP{
        public  String getFieldType(){
            return "java.util.Date";
        }
    };


    public abstract String getFieldType();
}

```

**从数据库中抽取数据**

```java
package com.fjh.codeAuto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DataUtil
 * Description:数据库元数据转换类
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


```

**生成文件，这里以controller为例，其他类型具体看源文件**

```java
package com.fjh.codeAuto;

import com.fjh.controller.FreeMarkerUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GenController
 * Description:
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

```

**模板文件ftl展示,这里单单展示实体类模板文件，具体文件看源代码**

```java
package ${entityPackageName};
<#list fields as field>
    <#if field.typeName == "java.lang.String">
    <#else>
    import ${field.typeName};
    </#if>
</#list>

/**
* ClassName:${entityClassName}
* Description:
* date: ${date?datetime}
*/
public class ${entityClassName} {

<#list fields as field>
    /**
    * ${field.remarks}
    */
    private ${(field.typeName?split("."))?last} ${field.columnName};
</#list>

<#list fields as field>
    public ${(field.typeName?split("."))?last} get${field.columnName?cap_first}() {
        return ${field.columnName};
    }

    public void set${field.columnName?cap_first}(${(field.typeName?split("."))?last} ${field.columnName}) {
        this.${field.columnName} = ${field.columnName};
    }
</#list>


}
```



### 最后

模板根据目标代码制定，数据来源我们根据实际情况获取，如此便可diy自己的autocode，可以自己写属于自己的模板减少那些搬砖工作，加油！：）

