package com.mdlicht.zb.exampleproject.room.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivityRoomBinding
import com.mdlicht.zb.exampleproject.room.adapter.UserRvAdapter
import com.mdlicht.zb.exampleproject.room.dialog.NewUserDialog
import com.mdlicht.zb.exampleproject.room.model.repository.UserRepository
import com.mdlicht.zb.exampleproject.room.viewmodel.RoomViewModel
import com.mdlicht.zb.exampleproject.room.viewmodel.factory.RoomViewModelFactory

class RoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.lifecycleOwner = this
        binding.vm =
            ViewModelProviders.of(this, RoomViewModelFactory(UserRepository(this))).get(RoomViewModel::class.java)
                .apply {
                    clickAdd.observe(this@RoomActivity, Observer {
                        NewUserDialog.newInstance().show(supportFragmentManager, null)
                    })
                    clickUpdate.observe(this@RoomActivity, Observer {
                        NewUserDialog.newInstance(it).show(supportFragmentManager, null)
                    })
                    clickDelete.observe(this@RoomActivity, Observer {
                        AlertDialog.Builder(this@RoomActivity).setTitle(R.string.room_delete_dialog_title)
                            .setMessage(R.string.room_delete_dialog_msg)
                            .setPositiveButton(R.string.common_yes) { dialog, _ ->
                                binding.vm!!.deleteUser(it!!)
                                dialog.dismiss()
                            }
                            .setNegativeButton(
                                R.string.common_cancel
                            ) { dialog, _ ->
                                dialog.dismiss()
                            }.show()
                    })
                }

        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@RoomActivity)
            adapter = UserRvAdapter(binding.vm!!)
            addItemDecoration(DividerItemDecoration(this@RoomActivity, DividerItemDecoration.VERTICAL))
        }
    }
}
