package com.mdlicht.zb.exampleproject.mvppractice.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import kotlinx.android.synthetic.main.item_mvp_practice_header.view.*

class HeaderViewHolder(itemView: View): BaseViewHolder<GitHubProfile>(itemView) {
    override fun onBindView(position: Int, item: GitHubProfile) {
        itemView.apply {
            Glide.with(this)
                .asBitmap()
                .load(item.avatar_url)
                .apply(RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher))
                .thumbnail(0.1f)
                .into(ivProfilePhoto)

            tvUserName.text = item.login

            btnProfile.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.html_url)))
            }
        }
    }
}