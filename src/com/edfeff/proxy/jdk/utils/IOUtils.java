package com.edfeff.proxy.jdk.utils;

import com.edfeff.proxy.jdk.support.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wangpp
 */
public class IOUtils {
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int len;
        byte[] buffer = new byte[Constant.IO_BUFFER_SIZE];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }
}
