package com.fjh.dao;

import com.fjh.entity.AutoCode;

import java.util.Date;

/**
 * ClassName: TestDao
 * Description:
 * date: 2019/9/9 15:58
 *
 * @author 冯佳豪
 */
public class TestDao {
    public static void main(String[] args) {
        AutoCodeDao autoCodeDao = new AutoCodeDao();
        AutoCode autoCode = new AutoCode();
      /*  //测试查询
        autoCode.setId(2);
        autoCodeDao.queryById(autoCode);
*/
        //测试保存
 /*       autoCode.setId(13);
        autoCode.setBirth(new Date());
        autoCode.setName("fhauigai");
        autoCode.setPrice(98.7f);
        autoCode.setUserName("fagayghaghu");
        autoCodeDao.save(autoCode);*/


        //测试修改
   /*     autoCode.setId(133);
        autoCode.setUserName("哈哈哈哈哈哈哈");
        autoCode.setName("你好");
        autoCodeDao.update(autoCode,13);*/

        //测试删除
    /*    autoCode.setId(133);
        autoCodeDao.delete(autoCode);*/

    }
}
