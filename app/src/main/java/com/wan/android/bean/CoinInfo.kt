package com.wan.android.bean

class CoinInfo { // 积分和排名可能不是实时的，每天更新
    /**
     * "coinCount":1580,
     * "level":16,
     * "rank":373,"
     * userId":9477,
     * "username":"1**4806539@qq.com"
     */
    var coinCount: Int = 0
    var level: Int = 0
    var rank: Int = 0
    var userId: Int = 0
    var nickname: String? = null
    var username: String? = null
}