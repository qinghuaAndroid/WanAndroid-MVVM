package com.qh.wanandroid.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 * Used for json converter
 *
 * @author zhilong [Contact me.](mailto:zhilong.lzl@alibaba-inc.com)
 * @version 1.0
 * @since 2017/4/10 下午2:10
 */
@Route(path = "/wanandroid/json")
class JsonServiceImpl : SerializationService {
    override fun init(context: Context) {}
    override fun <T> json2Object(text: String, clazz: Class<T>): T {
        return JSON.parseObject(text, clazz)
    }

    override fun object2Json(instance: Any): String {
        return JSON.toJSONString(instance)
    }

    override fun <T> parseObject(input: String, clazz: Type): T {
        return JSON.parseObject(input, clazz)
    }
}