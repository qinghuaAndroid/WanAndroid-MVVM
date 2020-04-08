package com.example.devlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.devlibrary.app.App;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class SpUtils {
    private static final String SP_NAME = "";

    /**
     * 清楚全部数据
     */
    public static void clearAll() {
        getSp().edit().clear().apply();
    }

    private static SharedPreferences getSp() {
        return SpHolder.sPreferences;
    }

    /**
     * 删除指定key对应的数据
     */
    public static void clear(String key) {
        getSp().edit().remove(key).apply();
    }

    /**
     * 查询某个key是否存在
     */
    public static boolean contains(String key) {
        return getSp().contains(key);
    }

    /**
     * 获取所有存储数据
     */
    public static Map<String, ?> getAll() {
        return getSp().getAll();
    }


    public static void put(String key, Object value) {
        SharedPreferences.Editor editor = getSp().edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            editor.putString(key, serialize(value));
        }
        editor.apply();
    }

    /**
     * 序列化对象
     */
    private static <T> String serialize(T value) {
        String serStr = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            serStr = baos.toString("ISO-8859-1");
            serStr = URLEncoder.encode(serStr, "UTF-8");
            CloseUtils.close(baos, oos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serStr;
    }

    public static <T> T get(String key, T defValue) {
        Object value = null;
        if (defValue instanceof Integer) {
            value = getSp().getInt(key, (Integer) defValue);
        } else if (defValue instanceof Float) {
            value = getSp().getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            value = getSp().getLong(key, (Long) defValue);
        } else if (defValue instanceof Boolean) {
            value = getSp().getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof String) {
            value = getSp().getString(key, (String) defValue);
        } else {
            value = deSerialization(getSp().getString(key, serialize(defValue)));
        }
        return (T) value;
    }

    /**
     * 序列化对象
     */
    private static <T> T deSerialization(String value) {
        T deSerStr = null;
        try {
            String redStr = URLDecoder.decode(value, "UTF-8");
            ByteArrayInputStream bais = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            ObjectInputStream ois = new ObjectInputStream(bais);
            deSerStr = (T) ois.readObject();
            CloseUtils.close(bais, ois);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return deSerStr;
    }

    static class SpHolder {
        static SharedPreferences sPreferences =
                App.sContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}

