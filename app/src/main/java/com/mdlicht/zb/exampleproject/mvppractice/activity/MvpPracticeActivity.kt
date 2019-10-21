package com.mdlicht.zb.exampleproject.mvppractice.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.mvppractice.adapter.MvpPracticeRvAdapter
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.repository.GitHubRepositoryImpl
import com.mdlicht.zb.exampleproject.mvppractice.presenter.MvpPracticePresenter
import kotlinx.android.synthetic.main.activity_mvp_practice.*

class MvpPracticeActivity : BaseActivity(), MvpPracticeConstract.View {
    private var presenter: MvpPracticePresenter? = null
    private val rvAdapter: MvpPracticeRvAdapter by lazy {
        MvpPracticeRvAdapter(this@MvpPracticeActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp_practice)
        initPresenter()
    }

    override fun initPresenter() {
        presenter = MvpPracticePresenter(this, rvAdapter, rvAdapter, GitHubRepositoryImpl())
    }

    override fun initView() {
        etUserName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter?.searchRepository(getKeyword())
                true
            } else {
                false
            }
        }

        rvList.apply {
            layoutManager = LinearLayoutManager(this@MvpPracticeActivity)
            adapter = rvAdapter
            addItemDecoration(DividerItemDecoration(this@MvpPracticeActivity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun getKeyword(): String = etUserName.text.toString()

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
