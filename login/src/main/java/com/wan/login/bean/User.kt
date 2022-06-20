package com.wan.login.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = User.TABLE_NAME)
data class User(

    /**
     * admin : false
     * chapterTops : []
     * collectIds : [10479,12202,12148,10916,12175]
     * email :
     * icon :
     * id : 36628
     * nickname : 18616720137
     * password :
     * publicName : 18616720137
     * token :
     * type : 0
     * username : 18616720137
     */

    var admin: Boolean = false,
    var email: String? = null,
    var icon: String? = null,
    @PrimaryKey var id: Int = 0,
    var nickname: String? = null,
    var password: String? = null,
    var publicName: String? = null,
    var token: String? = null,
    var type: Int = 0,
    var username: String? = null,
    var chapterTops: List<String>? = null,
    var collectIds: List<Int>? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME = "user"
    }
}
