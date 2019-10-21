package com.mdlicht.zb.exampleproject.room.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import com.mdlicht.zb.exampleproject.room.viewmodel.RoomViewModel

class RoomViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RoomViewModel(repository) as T
    }
}