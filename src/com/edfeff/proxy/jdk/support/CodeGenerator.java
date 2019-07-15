package com.edfeff.proxy.jdk.support;

import com.edfeff.proxy.jdk.demo.IA;
import com.edfeff.proxy.jdk.demo.IB;
import com.edfeff.proxy.jdk.utils.ClassUtils;
import com.edfeff.proxy.jdk.utils.ExceptionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理类代码生成器
 *
 * @author wangpp
 */
public class CodeGenerator {

    public static String generator(Class<?>[] interfaces, String packageName, String className) {
        CodeHelper ch = new CodeHelper();
        //包
        ch.addPackage(packageName);
        ch.addImport(interfaces);
        //导入其他依赖
        ch.addImport("java.lang.reflect.*");

        String interfacesName = ClassUtils.joinClassName(interfaces);

        //定义类
        //------- 类开始 <<< 1
        ch.add(ClassUtils.getClassSignature(interfaces, className)).addOpenBrace();

        //定义 属性 构造函数
        ch.add(ClassUtils.getDefaultFiledAndConstructor(className)).addBlankLine();

        //添加方法
        for (Class c : interfaces) {
            for (Method m : c.getMethods()) {

                String throwExceptionStr = getThrowExceptionStr(m, ",");
                if (throwExceptionStr != "") {
                    throwExceptionStr = " throws " + throwExceptionStr;
                }
                ch.add("@Override");
                ch.add("public " + m.getReturnType().getName()
                        + " " + m.getName()
                        + "(" + getMethodParams(m) + ")"
                        + throwExceptionStr + "{");

                //有返回值
                if (!"void".equals(m.getReturnType().getName())) {
                    ch.add(m.getReturnType().getName() + " result=null;");
                }
                //没有返回值的函数
                ch.add("try{");

                //调用
                if (!"void".equals(m.getReturnType().getName())) {
                    ch.add("result =(" + m.getReturnType().getName() + ")");
                }

                ch.add("h.invoke(this,");
                String methodParamsType = getMethodParamsType(m);
                if (methodParamsType != "") {
                    methodParamsType = "," + methodParamsType;
                }
                ch.add(c.getName() + ".class.getMethod(\""
                        + m.getName()
                        + "\""
                        + methodParamsType
                        + "),");

                String methodParamsNames = getMethodParamsNames(m);
                if (methodParamsNames != "") {
                    methodParamsNames = "new Object[]{" + methodParamsNames + "}";
                } else {
                    methodParamsNames = "null";
                }

                ch.add(methodParamsNames);

                ch.add(");");
                ch.add("}");
                //调用结束 异常处理
                //有抛出异常的
                //对异常的级别排序
                List<Class> exceptionOrderList = ExceptionUtils.sortExceptionOfMethod(m);
                if (exceptionOrderList != null) {
                    for (Class e : exceptionOrderList) {
                        ch.add("catch(" + e.getName() + " e){");
                        ch.add("throw e;");
                        ch.add("}");
                    }
                }
                // 处理 抛出异常是Throwable的， 如果本身就抛出Throwable的话，就不再添加此代码
                if (exceptionOrderList == null || !(exceptionOrderList.get(exceptionOrderList.size() - 1) == Throwable.class)) {
                    ch.add("catch(Throwable e){");
                    //没抛出异常的
                    ch.add("e.printStackTrace();");
                    ch.add("}");
                }

                //有返回值
                if (!"void".equals(m.getReturnType().getName())) {
                    ch.add("return result;");
                }
                ch.add("}");
                ch.addBlankLine();
            }
        }
        //------- 类结束 >>> 1
        ch.add("}");
        return ch.code();
    }

    /**
     * 获取抛出的异常
     *
     * @param m
     * @param split
     * @return
     */
    private static String getThrowExceptionStr(Method m, String split) {
        StringBuilder s = new StringBuilder();
        Class<?>[] exceptionTypes = m.getExceptionTypes();
        if (exceptionTypes == null || exceptionTypes.length < 1) {
            return "";
        }
        for (int i = 0; i < exceptionTypes.length; i++) {
            s.append(exceptionTypes[i].getName()).append(split);
        }
        return s.substring(0, s.length() - split.length());
    }

    /**
     * 获取方法的参数名
     *
     * @param method
     * @return
     */
    private static String getMethodParamsNames(Method method) {
        StringBuilder s = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length < 1) {
            return "";
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            s.append(" arg").append(i).append(",");
        }
        return s.substring(0, s.length() - 1);
    }

    /**
     * 获取方法的参数带类型
     *
     * @param method
     * @return
     */
    private static String getMethodParams(Method method) {
        StringBuilder s = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length < 1) {
            return "";
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            s.append(parameterTypes[i].getName()).append(" arg").append(i).append(",");
        }
        return s.substring(0, s.length() - 1);
    }

    /**
     * 获取方法参数类型列表
     *
     * @param method
     * @return
     */
    private static String getMethodParamsType(Method method) {
        StringBuilder s = new StringBuilder();

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length < 1) {
            return "";
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            s.append(parameterTypes[i].getName()).append(".class").append(",");
        }
        return s.substring(0, s.length() - 1);
    }


    public static void main(String[] args) {
        String generator3 = generator(new Class[]{IA.class, IB.class,}, Constant.PROXY_PACKAGE, "$Proxy0");
        System.out.println();
        System.out.println(generator3);
    }
}
