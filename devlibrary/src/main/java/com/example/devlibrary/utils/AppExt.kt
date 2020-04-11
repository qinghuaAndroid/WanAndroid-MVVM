package com.example.devlibrary.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

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