package com.example.devlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Cy on 2018/9/20.
 */
public class CacheUtils {
    /**
     * 获取应用缓存大小
     */
    public static String getTotalCacheSize(Context context) {

        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 清除应用缓存
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }

    /**
     * 获取文件
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
     * 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() -->
     * SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    private static Long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                // 如果下面还有文件
                if (aFileList.isDirectory()) {
                    size += getFolderSize(aFileList);
                } else {
                    size += aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;

        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(kiloByte);
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;

        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(megaByte);
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;

        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(gigaByte);
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
