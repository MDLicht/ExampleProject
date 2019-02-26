package com.mdlicht.zb.exampleproject.room.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import com.mdlicht.zb.exampleproject.room.viewmodel.NewUserViewModel
import com.mdlicht.zb.exampleproject.room.viewmodel.RoomViewModel

class NewUserViewModelFactory(val id: Int, private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewUserViewModel(id, repository) as T
    }
}