package com.edfeff.proxy.jdk;

import com.edfeff.proxy.jdk.support.Constant;
import com.edfeff.proxy.jdk.utils.IOUtils;

import java.io.*;

/**
 * 自定义的ClassLoader 从指定的文件中加载
 *
 * @author wangpp
 */
public class WClassLoader extends ClassLoader {

    public Class<?> findClassFromFile(String className, File file) {
        className = Constant.PROXY_PACKAGE + "." + className;
        try (
                FileInputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            IOUtils.copy(in, out);
            return defineClass(className, out.toByteArray(), 0, out.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class SingletonHolder {
        public static WClassLoader instance = new WClassLoader();
    }

    /**
     * 从此处获取ClassLoader实例
     *
     * @return
     */
    public static WClassLoader getInstance() {
        return SingletonHolder.instance;
    }
}
