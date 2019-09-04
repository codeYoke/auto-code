package ${daoPackageName};

import ${entityType};
import com.fjh.codeAuto.DbUtil;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ${daoClassName}{
    Connection conn = null;
    PreparedStatement pstmt = null;
    /**
    * 保存
    *
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

    //删除

    //修改

    //查询
}
