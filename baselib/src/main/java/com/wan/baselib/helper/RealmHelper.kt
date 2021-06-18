package com.wan.baselib.helper

import io.realm.Realm
import io.realm.RealmConfiguration

object RealmHelper {

    private const val DATABASE_NAME = "WanAndroid.realm"

    val realm: Realm
        get() = newRealmInstance()

    private fun newRealmInstance(): Realm {
        return Realm.getInstance(
            RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        )
    }
}