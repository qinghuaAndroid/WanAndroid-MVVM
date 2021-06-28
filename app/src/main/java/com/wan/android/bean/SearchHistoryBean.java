package com.wan.android.bean;

import io.realm.RealmObject;

/**
 * @author cy
 * Create at 2020/4/14.
 */
public class SearchHistoryBean extends RealmObject {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
