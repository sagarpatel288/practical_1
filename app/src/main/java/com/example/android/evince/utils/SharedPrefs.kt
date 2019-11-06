package com.example.android.evince.utils

import android.content.Context
import android.content.SharedPreferences

import com.example.android.evince.constants.AppConstants


/**
 * Created by srdpatel on 2/25/2018.
 * Uses reflection-proof, serialization-proof, thread safe (AsyncTask, AsyncLoader), Double check lock, volatile and lazy (initialization) singleton pattern for SharedPreferences and SharedPreferences.Editor objects
 *
 * @see [http://google.com](https://www.ibm.com/developerworks/library/j-dcl/index.html)
 *
 * @since 1.0
 */

class SharedPrefs//Private constructor
private constructor() {

    init {
        //Prevent from the reflection
        if (mSharedPrefs != null) {
            throw RuntimeException(AppConstants.STR_MSG_ERROR_SHARED_PREF_REFLECTION)
        }
        //Prevent from the reflection
        if (mSharedPreferences != null) {
            throw RuntimeException(AppConstants.STR_MSG_ERROR_SHARED_PREF_REFLECTION)
        }
        //Prevent from the reflection
        if (mEditor != null) {
            throw RuntimeException(AppConstants.STR_MSG_ERROR_SHARED_PREF_REFLECTION)
        }
    }

    /**
     * Protects from serialization and deserialization
     *
     * @since 1.0
     */
    protected fun readResolve(): SharedPrefs? {
        return instance
    }

    companion object {

        @Volatile
        private var mSharedPrefs: SharedPrefs? = null
        @Volatile
        private var mSharedPreferences: SharedPreferences? = null
        @Volatile
        private var mEditor: SharedPreferences.Editor? = null

        /**
         * Gives SharedPreferences.Editor with secure singleton pattern
         *
         *
         * Uses thread safety and double check lock on volatile return type
         *
         *
         *
         * @param mContext Context
         * @return singleton and volatile SharedPreferences.Editor
         * see [.mEditor]
         * @since 1.0
         */
        fun getEditor(mContext: Context): SharedPreferences.Editor? {
            //Double check locking
            if (mEditor == null) { //Checking for the first time
                synchronized(SharedPrefs::class.java) {
                    if (mEditor == null) { //Check for second time
                        mEditor = getSharedPref(mContext)!!.edit()
                    }
                }
            }
            return mEditor
        }

        fun savePrefs(context: Context, key: String, value: String) {
            SharedPrefs.getEditor(context)!!.putString(key, value).apply()
        }

        fun saveInt(context: Context, key: String, value: Int) {
            SharedPrefs.getEditor(context)!!.putInt(key, value).apply()
        }

        fun getString(context: Context, key: String, defaultValue: String): String? {
            return SharedPrefs.getSharedPref(context)!!.getString(key, defaultValue)
        }

        fun getInt(context: Context, key: String, defaultValue: Int): Int {
            return SharedPrefs.getSharedPref(context)!!.getInt(key, defaultValue)
        }

        /**
         * Gives SharedPreferences with secure singleton pattern
         *
         *
         * Uses thread safety and double check lock on volatile return type
         *
         *
         *
         * @param mContext Context
         * @return singleton and volatile SharedPreferences
         * see [.mSharedPreferences]
         * @since 1.0
         */
        fun getSharedPref(mContext: Context): SharedPreferences? {
            //Double check locking
            if (mSharedPreferences == null) { //Checking for the first time
                synchronized(SharedPrefs::class.java) {
                    if (mSharedPreferences == null) { //Check for second time
                        mSharedPreferences = mContext.getSharedPreferences(AppConstants.STR_SHARED_PREF,
                                Context.MODE_PRIVATE) //Create new instance only if there is no instance ever created before
                    }
                }
            }
            return mSharedPreferences
        }

        /**
         * Gives SharedPrefs instance of this class with secure singleton pattern
         *
         *
         * Uses thread safety and double check lock on volatile return type
         *
         *
         *
         * @return singleton and volatile SharedPrefs
         * see [.mSharedPrefs]
         * @since 1.0
         */
        //Double check locking
        //Checking for the first time
        //Check for second time
        //Create new instance only if there is no instance ever created before
        val instance: SharedPrefs?
            get() {
                if (mSharedPrefs == null) {
                    synchronized(SharedPrefs::class.java) {
                        if (mSharedPrefs == null) {
                            mSharedPrefs = SharedPrefs()
                        }
                    }
                }
                return mSharedPrefs
            }
    }
}