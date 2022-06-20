package com.wan.login.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wan.login.bean.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getUser(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}