package com.edfeff.proxy.jdk;

import com.edfeff.proxy.jdk.support.Constant;
import com.edfeff.proxy.jdk.utils.FileUtils;
import com.edfeff.proxy.jdk.support.CodeGenerator;
import com.edfeff.proxy.jdk.utils.CompilerUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代理类生成主类
 *
 * @author wangpp
 */
public class WProxy {
    /**
     * 中间java文件是否删除
     */
    private static boolean deleteJavaFile = true;
    /**
     * 中间class文件是否删除
     */
    private static boolean deleteClassFile = true;

    private static AtomicInteger num = new AtomicInteger(0);

    private static String packageName = Constant.PROXY_PACKAGE;

    private static int getNum() {
        return num.getAndIncrement();
    }

    public static Object newProxyInstance(WClassLoader classLoader, Class<?>[] interfaces, WInvocationHandler h) {

        Objects.requireNonNull(h);

        String className = getClassName();

        //缓存处理
        Class proxyClass = getOrCreate(classLoader, interfaces, className);

        //实例化,设置属性
        try {
            Constructor constructor = proxyClass.getConstructor(WInvocationHandler.class);
            return constructor.newInstance(h);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class getOrCreate(WClassLoader classLoader, Class<?>[] interfaces, String className) {
        return generateClass(classLoader, interfaces, className);
    }

    public static Class generateClass(WClassLoader classLoader, Class<?>[] interfaces, String className) {
        Class proxyClass = null;

        //编写代理类源码
        String sourceCode = CodeGenerator.generator(interfaces, packageName, className);
        //写入文件
        File javaSourceFile = getFileName(packageName, className, Constant.JAVA_SUFFIX);
        FileUtils.saveStringToFile(sourceCode, javaSourceFile);

        //编译文件 删除源代码文件
        try {
            CompilerUtils.compiler(javaSourceFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (deleteJavaFile) {
                //删除java文件
                FileUtils.deleteFile(javaSourceFile);
            }
        }

        //获取class文件
        File javaClassFile = getFileName(packageName, className, Constant.CLASS_SUFFIX);
        //加载类，删除编译文件
        proxyClass = classLoader.findClassFromFile(className, javaClassFile);
        if (deleteClassFile) {
            //删除java文件
            FileUtils.deleteFile(javaClassFile);
        }
        return proxyClass;
    }

    /**
     * 根据package和class生成File
     *
     * @param packageName
     * @param className
     * @return
     */
    private static File getFileName(String packageName, String className, String suffix) {

        String qualifiedClassName = packageName + "." + className;

        String fileName = qualifiedClassName.replaceAll("\\.", "/") + suffix;

        String path = WProxy.class.getClassLoader().getResource("").getPath();

        File file = new File(path, fileName);

        return file;
    }

    private static String getClassName() {
        return Constant.PROXY_CLASS_PREFIX + getNum();
    }

    public static boolean isDeleteJavaFile() {
        return deleteJavaFile;
    }

    public static void setDeleteJavaFile(boolean deleteJavaFile) {
        WProxy.deleteJavaFile = deleteJavaFile;
    }

    public static boolean isDeleteClassFile() {
        return deleteClassFile;
    }

    public static void setDeleteClassFile(boolean deleteClassFile) {
        WProxy.deleteClassFile = deleteClassFile;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static void setPackageName(String packageName) {
        WProxy.packageName = packageName;
    }

}
