package com.lianup.catalina.processor;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 本来是想用lifecycle来把loader和对应的servlet绑定起来，servlet起了loder就起这样子
 */
public class SimpleLoader {

    public SimpleLoader(String SERVLET_PACAGE_URL){
        this.SERVLET_PACAGE_URL = SERVLET_PACAGE_URL;
    }

    private Class servletClass;
    private URLClassLoader URL_CLASS_LOADER;
    /**
     * 注意classloader寻找.class文件的时候，进入项目报名就是文件名的一部分，而不是用/了。
     */
    private final String SERVLET_PACAGE_URL;

    private static final String CLASSES_FILE_URL = "F:/spring/noname/target/classes";

     {

        //定位到resouces/servlet文件夹
        URL serveletClassPath = null;
        try {
            serveletClassPath = new File(CLASSES_FILE_URL).toURI().toURL();
        }catch (Exception e){
            e.printStackTrace();
        }
        URL_CLASS_LOADER = new URLClassLoader(new URL[]{serveletClassPath});
    }


    private String getServletName(String uri){
        return uri.substring(uri.lastIndexOf("/")+1);
    }


    public Class getServletClass(String servletName){

        try {
            servletClass = URL_CLASS_LOADER.loadClass(SERVLET_PACAGE_URL + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return servletClass;
    }

}
