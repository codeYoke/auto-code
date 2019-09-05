package com.fjh.dao;

import com.fjh.entity.AutoCode;
import com.fjh.codeAuto.DbUtil;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
* ClassName:
* Description:
* date: 2019-9-5 11:49:03
*/
public class AutoCodeDao{
    Connection conn = null;
    PreparedStatement pstmt = null;
    /**
    * 保存
    *
    */
    public void  save(AutoCode autoCode){
        String sql = "insert into auto_code (price,user_name,name,birth,id)  values  (?, ?, ?, ?, ? )" ;
        List paramsValues = new ArrayList();
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            String fileldNames = "price,userName,name,birth,id";
            for(String fieldName : fileldNames.split(",")){
                Class clazz = AutoCode.class;
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(autoCode);
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
