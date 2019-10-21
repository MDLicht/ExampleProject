package com.mdlicht.zb.exampleproject.room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.text.TextUtils
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import java.util.*

class NewUserViewModel(val id: Int = 0, private val repository: UserRepository): ViewModel() {
    val name: ObservableField<String> = ObservableField()
    val desc: ObservableField<String> = ObservableField()

    val showToast: MutableLiveData<Boolean> = MutableLiveData()
    val dismissDialog: MutableLiveData<Boolean> = MutableLiveData()

    fun onCancelClick() {
        dismissDialog.value = true
    }

    fun onAddClick(id: Int, name: String?, desc: String?) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc)) {
            if (id == 0) {
                repository.addUser(User(0, name!!, desc!!, Date(System.currentTimeMillis()).toString()))
            } else {
                repository.updateUser(User(id, name!!, desc!!, Date(System.currentTimeMillis()).toString()))
            }
            dismissDialog.value = true
        } else {
            showToast.value = true
        }
    }
}