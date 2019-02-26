package com.mdlicht.zb.exampleproject.room.dialog


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.FragmentNewUserDialogBinding
import com.mdlicht.zb.exampleproject.room.model.User
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import com.mdlicht.zb.exampleproject.room.viewmodel.NewUserViewModel
import com.mdlicht.zb.exampleproject.room.viewmodel.factory.NewUserViewModelFactory

class NewUserDialog : DialogFragment() {
    lateinit var binding: FragmentNewUserDialogBinding
    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, 0)
        arguments?.let {
            user = it.getParcelable(KEY_USER_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_user_dialog, container, false)
        binding.vm = ViewModelProviders.of(this, NewUserViewModelFactory(user?.uid ?: 0, UserRepository(context!!))).get(NewUserViewModel::class.java).apply {
            dismissDialog.observe(this@NewUserDialog, Observer {
                dismissAllowingStateLoss()
            })
            showToast.observe(this@NewUserDialog, Observer {
                Toast.makeText(context, "Name or Desc is empty.", Toast.LENGTH_SHORT).show()
            })
            user?.let {
                name.set(it.name)
                desc.set(it.desc)
                binding.btnAdd.setText(R.string.room_dialog_btn_update)
            }
        }
        return binding.root
    }


    companion object {
        const val KEY_USER_DATA = "userData"
        @JvmStatic
        fun newInstance(user: User? = null) =
            NewUserDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_USER_DATA, user)
                }
            }
    }
}
