package com.mdlicht.zb.exampleproject.room.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.room.model.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    companion object {
        private const val DB_NAME = "Database"
        var INSTANCE: com.mdlicht.zb.exampleproject.room.database.Database? = null

        fun getDatabase(context: Context): com.mdlicht.zb.exampleproject.room.database.Database? {
            if (INSTANCE == null) {
                synchronized(Database::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        com.mdlicht.zb.exampleproject.room.database.Database::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun userDao(): UserDao
}