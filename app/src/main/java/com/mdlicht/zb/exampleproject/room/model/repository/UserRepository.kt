package com.mdlicht.zb.exampleproject.room.model.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.mdlicht.zb.exampleproject.room.database.Database
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.room.model.dao.UserDao

class UserRepository(val context: Context) {
    private val userDao: UserDao = Database.getDatabase(context)!!.userDao()

    fun getAllUser(): LiveData<List<User>> {
        return userDao.getAllUser()
    }

    fun addUser(user: User) {
        userDao.insertUser(user)
    }

    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}