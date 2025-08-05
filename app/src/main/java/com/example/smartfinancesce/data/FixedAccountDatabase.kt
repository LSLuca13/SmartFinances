package com.example.smartfinancesce.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FixedAccount::class], version = 1)
abstract class FixedAccountDatabase : RoomDatabase() {
    abstract fun fixedAccountDao(): FixedAccountDao

    companion object {
        @Volatile
        private var INSTANCE: FixedAccountDatabase? = null

        fun getInstance(context: Context): FixedAccountDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FixedAccountDatabase::class.java,
                    "fixed_account_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
