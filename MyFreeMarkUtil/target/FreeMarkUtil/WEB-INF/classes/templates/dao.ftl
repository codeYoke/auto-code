package ${daoPackageName};

import ${entityType};
import com.fjh.codeAuto.DbUtil;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
* ClassName:${daoClassName}
* Description:
* date: ${date?datetime}
*/
public class ${daoClassName}{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    /**
    * MethodName :保存
    * Description: 将一个属性与数据库表属性相对应的对象插入到数据库中
    * @param ${entityParamName?uncap_first} 数据对象
    */
    public void  save(${(entityType?split("."))?last} ${entityParamName?uncap_first}){
        <#assign sql = "insert into ${tableName} (" >
        <#assign params= "(">
        <#assign fieldNames = "" ><#--所有的属性拼接到字符串中-->
        <#-- //String sql = "insert into ${tableName} ("  //(name,price,birth,user_name) values (?,?,?,?)";-->
        <#compress >
            <#list columnReProperties?keys as key><#--key是数据库的列名   value是属性的名称-->
                <#if key?is_last>
                    <#assign sql = sql + key + ")" >
                    <#assign params = params  + "? )" >
                    <#assign fieldNames = fieldNames  + columnReProperties[key] >
                <#else >
                    <#assign sql = sql + key + "," >
                    <#assign params = params  + "?, " >
                    <#assign fieldNames = fieldNames   + columnReProperties[key] + ",">
                </#if>
            </#list>
        </#compress>
        String sql = "${sql}  values  ${params}" ;
        List paramsValues = new ArrayList();
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            String fileldNames = "${fieldNames}";
            for(String fieldName : fileldNames.split(",")){
                Class clazz = ${(entityType?split("."))?last}.class;
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(${entityParamName?uncap_first});
                paramsValues.add(value);
            }
            for(int i = 1; i <= paramsValues.size(); i ++){
                pstmt.setObject(i, paramsValues.get(i-1));
            }
            pstmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DbUtil.close(conn,pstmt);
        }

    }

    /**
     * MethodName: 删除
     * Description: 将一个属性与数据库表属性相对应的对象从数据库中删除(通过设置该对象id,通过id删除信息记录)
     * @param ${entityParamName?uncap_first} 数据对象
     */
    public void delete(${(entityType?split("."))?last} ${entityParamName?uncap_first}){
        //	delete from stu  where stu_id = ?
        String sql = "delete from ${tableName}  where id = ?" ;
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            Class clazz = ${(entityType?split("."))?last}.class;
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object value = field.get(${entityParamName?uncap_first});
            pstmt.setObject(1,value);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * MethodName 修改
     * Description 通过id修改数据库表
     * @param ${entityParamName?uncap_first} 与数据库表对应的对象
     * @param id 需要修改的id
     */
    public void update(${(entityType?split("."))?last} ${entityParamName?uncap_first},int id) {
        // update stu set stu_no = ? where stu_id = ?;
        <#assign sql = "update ${tableName} set " >
        <#assign fieldNames = "" ><#--所有的属性拼接到字符串中-->
        <#compress >
            <#list columnReProperties?keys as key><#--key是数据库的列名   value是属性的名称-->
                <#if key?is_last>
                    <#assign sql = sql + key + " = ? " >
                    <#assign fieldNames = fieldNames  + columnReProperties[key] >
                <#else >
                    <#assign sql = sql + key + " = ?, " >
                    <#assign fieldNames = fieldNames   + columnReProperties[key] + ",">
                </#if>
            </#list>
        </#compress>
        String sql = "${sql}where id = "+id;
        List paramsValues = new ArrayList();
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            String fileldNames = "${fieldNames}";
            for (String fieldName : fileldNames.split(",")) {
                Class clazz =${(entityType?split("."))?last}.class;
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(${entityParamName?uncap_first});
                paramsValues.add(value);
            }
            for (int i = 1; i <= paramsValues.size(); i++) {
                pstmt.setObject(i, paramsValues.get(i - 1));
            }
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * MethodName 查询
     * Description 通过id查询数据库信息记录(通过设置该对象id,通过id查询信息记录)
     * @param ${entityParamName?uncap_first} 数据对象
     */
    public void queryById(${(entityType?split("."))?last} ${entityParamName?uncap_first}){
        // select * from stu where stu_id = ?
        String sql = "select * from ${tableName} where id = ?" ;
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            Class clazz = ${(entityType?split("."))?last}.class;
            // 通过字节码对象获取其指定注解值
            // 获取所有声明属性
            Field[] declaredFields = clazz.getDeclaredFields();
            //获取单个属性值
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object value = field.get(${entityParamName?uncap_first});
            pstmt.setObject(1,value);
            rs = pstmt.executeQuery();
            // 获取元数据
            ResultSetMetaData metaData = rs.getMetaData();
            // 获取元数据多少列
            int columnCount = metaData.getColumnCount();
            // 获取查询的值
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object data = rs.getObject(i);
                    Object object = data;
                    String s = metaData.getColumnLabel(i) + ":" + object;
                    try {
                        Object param = rs.getObject(i);
                        declaredFields[i - 1].setAccessible(true);
                        declaredFields[i - 1].set(${entityParamName?uncap_first}, param);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
