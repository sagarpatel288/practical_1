package com.example.android.evince.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.evince.pojo.Matrix
import com.example.android.evince.utils.SharedPrefs.Companion.instance

@Database(entities = [Matrix::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao() : AppDao

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        public val DATABASE_NAME = "evince.db"
        @Volatile
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context)= sInstance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { sInstance = it}
        }

        fun getDatabase(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class) {
                    sInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries().build()
                }
            }
            return sInstance
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }