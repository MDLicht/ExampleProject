package com.mdlicht.zb.exampleproject.mvppractice.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.mvppractice.adapter.MvpPracticeRvAdapter
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData
import com.mdlicht.zb.exampleproject.mvppractice.presenter.MvpPracticePresenter
import kotlinx.android.synthetic.main.activity_mvp_practice.*

class MvpPracticeActivity : BaseActivity(), MvpPracticeConstract.View {
    private var presenter: MvpPracticePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp_practice)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = MvpPracticePresenter(this)
    }

    override fun initView() {
        etUserName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter?.searchRepository(v.text.toString())
                true
            } else {
                false
            }
        }

        rvList.apply {
            layoutManager = LinearLayoutManager(this@MvpPracticeActivity)
            adapter = MvpPracticeRvAdapter(this@MvpPracticeActivity)
            addItemDecoration(DividerItemDecoration(this@MvpPracticeActivity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun updateData(data: List<GitHubData>?) {
        if(data.isNullOrEmpty()) {
            showEmpty()
        } else {
            showData()
            (rvList?.adapter as MvpPracticeRvAdapter).setDataSet(data)
        }
    }

    override fun showData() {
        rvList.visibility = View.VISIBLE
        tvEmpty.visibility = View.GONE
    }

    override fun showEmpty() {
        rvList.visibility = View.GONE
        tvEmpty.visibility = View.VISIBLE
    }

    override fun onStop() {
        presenter?.onClear()
        super.onStop()
    }
}
