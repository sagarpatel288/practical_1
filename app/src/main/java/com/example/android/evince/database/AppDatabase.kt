package com.example.android.evince.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.evince.pojo.Matrix

@Database(entities = [Matrix::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private val DATABASE_NAME = "evince.db"
        @Volatile
        private var sInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    sInstance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return sInstance
        }
    }
}