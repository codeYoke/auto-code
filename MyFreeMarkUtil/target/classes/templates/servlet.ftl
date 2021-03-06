package ${controllerPackageName};

import ${daoType};
import ${entityType};

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* ClassName:${controllerClassName}
* Description:
* date: ${date?datetime}
*/
@WebServlet(name = "${controllerClassName}",urlPatterns = "/${controllerClassName}")
public class ${controllerClassName} extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ${entityClassName} ${entityClassName?uncap_first} = (${entityClassName}) fillParams(${entityClassName}.class,request);
        ${daoClassName} ${daoClassName?uncap_first} = new ${daoClassName}();

        if(request.getParameter("type")=="save"||request.getParameter("type").equals("save")){
            //插入操作业务...
            ${daoClassName?uncap_first}.save(${entityClassName?uncap_first});
        }else if (request.getParameter("type")=="delete"||request.getParameter("type").equals("delete")){
            //删除操作业务...
            ${daoClassName?uncap_first}.delete(${entityClassName?uncap_first});
        }else if (request.getParameter("type")=="update"||request.getParameter("type").equals("update")){
            //修改操作业务...
            ${daoClassName?uncap_first}.update(${entityClassName?uncap_first},Integer.parseInt(request.getParameter("id")));   // 默认修改自身id
        }else if (request.getParameter("type")=="query"||request.getParameter("type").equals("query")){
            //查询操作业务...
            ${daoClassName?uncap_first}.queryById(${entityClassName?uncap_first});
        }else{
            System.err.println("动作类型不匹配！");
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("write something here!");

        writer.flush();
        writer.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


    private Object fillParams(Class<?> clazz,HttpServletRequest request){
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            //将参数对应的值都要获取 参数的名称和值的对应关系，参数的名称和servlet中的属性的名称是对应关系
            Map<String, String[]> parameterMap = request.getParameterMap();
            for(String key : parameterMap.keySet()) {
                try {
                    String [] values = parameterMap.get(key);
                    //key参数的名称 根据参数的名称判断当前的类中是否包含该名称的属性
                    Field field = obj.getClass().getDeclaredField(key);
                    //如果field存在
                    field.setAccessible(true); //a : [你好]  b : [123, 456]
                    //获取属性的类型
                    Class fieldTyepe = field.getType();
                    if(Integer.class == fieldTyepe || int.class == fieldTyepe) {
                        //表示当前的属性是一个整数类型数据
                        field.set(obj, Integer.parseInt(values[0]) );
                    } else if(Long.class == fieldTyepe || long.class == fieldTyepe) {
                        field.set(obj, Long.parseLong(values[0]) );
                    } else if (Float.class == fieldTyepe || float.class == fieldTyepe){
                        field.set(obj,Float.parseFloat(values[0]));
                    } else if(String[].class == fieldTyepe) {
                        List<String> list = new ArrayList<String>();
                        for(String s : values) {
                            if(!"".equals(s.trim())) {
                                //有效的值
                                list.add(s);
                            }
                        }

                        String [] temp = new String[list.size()];
                        for(int i = 0;i < list.size();i ++) {
                            temp[i] = list.get(i);
                        }

                        field.set(obj,temp);// values = [,,,]
                    } else if(int[].class == fieldTyepe) {
                        List<String> list = new ArrayList<String>();
                        for(String s : values) {
                            if(!"".equals(s.trim())) {
                                //有效的值
                                list.add(s);
                            }
                        }

                        int [] temp = new int[list.size()];
                        for(int i = 0;i < list.size();i ++) {
                            temp[i] = Integer.parseInt(list.get(i));
                        }

                        field.set(obj,temp);// values = [,,,]
                    }  else if(Date.class == fieldTyepe ) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        field.set(obj, sdf.parse(values[0])  );
                    } else {
                        //给属性赋值就需要知道类型
                        field.set(obj, values[0]);
                    }

                } catch ( NoSuchFieldException | SecurityException e) {
                    //判断是否是动作属性（客户端传过来用来判断执行动作的type的值）
                    if(key=="type"||key.equals("type")){
                        System.out.println("获取动作类型:"+parameterMap.get(key).toString());
                     }else {
                        //属性不存在
                        e.printStackTrace();
                    }

                } catch (NumberFormatException e) {
                    // 数字转换出错
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
