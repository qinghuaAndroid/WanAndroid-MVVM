package com.example.devlibrary.utils;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    /**
     * 关闭实现Closeable接口的对象
     *
     * @param closeables 可变参数、且参数对象实现Closeable接口
     */
    public static void close(Closeable... closeables) {
        for (int i = 0; i < closeables.length; i++) {
            if (closeables[i] != null) {
                try {
                    closeables[i].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
