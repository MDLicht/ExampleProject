package com.mdlicht.zb.exampleproject.room.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.DialogInterface
import android.view.View
import com.mdlicht.zb.exampleproject.room.adapter.UserRvAdapter
import com.mdlicht.zb.exampleproject.room.iface.OnUserClickListener
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import java.util.*

class RoomViewModel(private val repository: UserRepository) : ViewModel(), OnUserClickListener {
    var dataSet: LiveData<List<User>>? = null
    val clickAdd: MutableLiveData<Boolean> = MutableLiveData()
    val clickUpdate: MutableLiveData<User> = MutableLiveData()
    val clickDelete: MutableLiveData<User> = MutableLiveData()
    init {
        loadUsers()
    }

    fun loadUsers() {
        dataSet = repository.getAllUser()
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    fun addUser() {
        clickAdd.value = true
    }

    override fun onItemClick(user: User) {
        clickUpdate.value = user
    }

    override fun onItemLongClick(user: User): Boolean {
        clickDelete.value = user
        return true
    }
}