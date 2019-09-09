package com.fjh.dao;

import com.fjh.entity.AutoCode;
import com.fjh.codeAuto.DbUtil;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
* ClassName:AutoCodeDao
* Description:
* date: 2019-9-9 19:43:11
*/
public class AutoCodeDao{
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    /**
    * MethodName :保存
    * Description: 将一个属性与数据库表属性相对应的对象插入到数据库中
    * @param autoCode 数据对象
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

    /**
     * MethodName: 删除
     * Description: 将一个属性与数据库表属性相对应的对象从数据库中删除(通过设置该对象id,通过id删除信息记录)
     * @param autoCode 数据对象
     */
    public void delete(AutoCode autoCode){
        //	delete from stu  where stu_id = ?
        String sql = "delete from auto_code  where id = ?" ;
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            Class clazz = AutoCode.class;
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object value = field.get(autoCode);
            pstmt.setObject(1,value);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * MethodName 修改
     * Description 通过id修改数据库表
     * @param autoCode 与数据库表对应的对象
     * @param id 需要修改的id
     */
    public void update(AutoCode autoCode,int id) {
        // update stu set stu_no = ? where stu_id = ?;
        String sql = "update auto_code set price = ?, user_name = ?, name = ?, birth = ?, id = ? where id = "+id;
        List paramsValues = new ArrayList();
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            String fileldNames = "price,userName,name,birth,id";
            for (String fieldName : fileldNames.split(",")) {
                Class clazz =AutoCode.class;
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(autoCode);
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
     * @param autoCode 数据对象
     */
    public void queryById(AutoCode autoCode){
        // select * from stu where stu_id = ?
        String sql = "select * from auto_code where id = ?" ;
        try {
            conn = DbUtil.getCon();
            pstmt = conn.prepareStatement(sql);
            Class clazz = AutoCode.class;
            // 通过字节码对象获取其指定注解值
            // 获取所有声明属性
            Field[] declaredFields = clazz.getDeclaredFields();
            //获取单个属性值
            Field field = clazz.getDeclaredField("id");
            field.setAccessible(true);
            Object value = field.get(autoCode);
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
                        declaredFields[i - 1].set(autoCode, param);
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
