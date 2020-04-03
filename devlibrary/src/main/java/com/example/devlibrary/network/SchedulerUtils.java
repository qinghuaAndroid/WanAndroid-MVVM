package com.example.devlibrary.network;

/**
 * Created by Cy on 2018/9/27.
 */
public class SchedulerUtils {

    public static <T> IoMainScheduler<T>  ioToMain() {
        return new IoMainScheduler<T>();
    }

}
