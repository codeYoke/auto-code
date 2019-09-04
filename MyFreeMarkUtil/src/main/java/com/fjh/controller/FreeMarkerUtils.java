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
