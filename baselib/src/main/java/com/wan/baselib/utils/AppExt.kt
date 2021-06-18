package com.wan.baselib.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import java.util.*

/**
 * Created by Cy on 2018/8/7.
 */

val Context.versionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode: Long
    get() = with(packageManager.getPackageInfo(packageName, 0)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
    }

/**
 * 获取当前手机系统语言。
 *
 * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
 */
fun getSystemLanguage(): String? {
    return Locale.getDefault().language
}

/**
 * 获取当前手机系统版本号
 *
 * @return 系统版本号
 */
fun getSystemVersion(): String? {
    return Build.VERSION.RELEASE
}

/**
 * 获取手机型号
 *
 * @return 手机型号
 */
fun getSystemModel(): String? {
    return Build.MODEL
}

/**
 * 获取手机厂商
 *
 * @return 手机厂商
 */
fun getDeviceBrand(): String? {
    return Build.BRAND
}

/**
 * Get app signature by [packageName]
 */
fun Context.getAppSignature(packageName: String = this.packageName): ByteArray? {
    val packageInfo: PackageInfo =
        packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
    val signatures = packageInfo.signatures
    return signatures[0].toByteArray()
}

/**
 * Whether the application is installed
 */
fun Context.isPackageInstalled(pkgName: String): Boolean {
    return try {
        packageManager.getPackageInfo(pkgName, 0)
        true
    } catch (e: Exception) {
        false
    }
}