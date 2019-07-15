package com.edfeff.proxy.jdk.utils;

import com.edfeff.proxy.jdk.demo.IA;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wangpp
 */
public class ProxyUtils {
    /**
     * 获取代理类对象的文件
     *
     * @param className
     * @param fileName
     * @param interfaces
     * @return
     */
    public static File getProxyClassFile(String className, String fileName, Class<?>[] interfaces) {
        byte[] bytes = ProxyGenerator.generateProxyClass(className, interfaces);
        File file = new File(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) {
        File h = getProxyClassFile("h", "h.class", new Class[]{IA.class});
        System.out.println(h);
    }
}
