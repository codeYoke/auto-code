package com.fjh.testTemplate;

import com.fjh.dao.AutoCodeDao;
import com.fjh.entity.AutoCode;

import java.util.Date;

/**
 * ClassName: testDao
 * Description:
 * date: 2019/9/4 14:18
 *
 * @author 冯佳豪
 */
public class testDao {
    public static void main(String[] args) {
        AutoCode autoCode = new AutoCode();
        autoCode.setId(3);
        autoCode.setBirth(new Date());
        autoCode.setName("tom2");
        autoCode.setPrice(9.994f);
        autoCode.setUserName("jac2k");
        AutoCodeDao autoCodeDao = new AutoCodeDao();
        autoCodeDao.save(autoCode);

    }
}
