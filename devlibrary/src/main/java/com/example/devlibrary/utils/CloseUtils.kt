package com.example.devlibrary.utils

import java.io.Closeable
import java.io.IOException

object CloseUtils {
    /**
     * 关闭实现Closeable接口的对象
     *
     * @param closeables 可变参数、且参数对象实现Closeable接口
     */
    @JvmStatic
    fun close(vararg closeables: Closeable?) {
        for (i in closeables.indices) {
            if (closeables[i] != null) {
                try {
                    closeables[i]!!.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}