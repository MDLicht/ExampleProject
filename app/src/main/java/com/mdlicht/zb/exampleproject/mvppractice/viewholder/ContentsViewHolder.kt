package com.mdlicht.zb.exampleproject.mvppractice.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import com.mdlicht.zb.exampleproject.baserecyclerview.viewholder.BaseViewHolder
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import kotlinx.android.synthetic.main.item_mvp_practice_contents.view.*

class ContentsViewHolder(itemView: View): BaseViewHolder<GitHubRepo>(itemView) {
    override fun onBindView(position: Int, item: GitHubRepo) {
        itemView.apply {
            setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.html_url)))
            }

            tvRepoName.text = item.name

            tvStarCount.text = item.stargazers_count.toString()
        }
    }
}