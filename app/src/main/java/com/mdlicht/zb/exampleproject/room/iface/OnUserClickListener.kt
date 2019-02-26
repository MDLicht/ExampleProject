package com.mdlicht.zb.exampleproject.room.iface

import com.mdlicht.zb.exampleproject.room.model.User

interface OnUserClickListener {
    fun onItemClick(user: User)
    fun onItemLongClick(user: User): Boolean
}