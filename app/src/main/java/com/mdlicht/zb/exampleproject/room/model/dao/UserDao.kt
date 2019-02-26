package com.mdlicht.zb.exampleproject.room.model.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.mdlicht.zb.exampleproject.room.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE uid == :userId")
    fun getUserById(userId: Int): User

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun getUserByIds(userIds: IntArray): List<User>

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertAllUser(vararg user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}