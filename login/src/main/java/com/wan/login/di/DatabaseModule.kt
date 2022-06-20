package com.wan.login.di

import android.content.Context
import androidx.room.Room
import com.wan.login.db.AppDatabase
import com.wan.login.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}