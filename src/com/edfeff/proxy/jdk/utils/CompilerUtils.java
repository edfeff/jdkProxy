package com.edfeff.proxy.jdk.utils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;

/**
 * @author wangpp
 */
public class CompilerUtils {
    public static Boolean compiler(File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null,
                manager,
                null,
                null,
                null,
                iterable);
        Boolean call = task.call();
        manager.close();
        return call;
    }

}
