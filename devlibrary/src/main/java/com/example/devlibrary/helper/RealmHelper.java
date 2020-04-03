package com.example.devlibrary.helper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmHelper {
    private static final String DATABASE_NAME = "WanAndroid.realm";

    public static Realm newRealmInstance(){
        return Realm.getInstance(new RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .deleteRealmIfMigrationNeeded()
                .build());
    }
}
