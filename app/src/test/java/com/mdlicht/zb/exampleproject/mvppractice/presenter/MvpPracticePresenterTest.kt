package com.mdlicht.zb.exampleproject.mvppractice.presenter

import com.mdlicht.zb.exampleproject.mvppractice.activity.MvpPracticeActivity
import com.mdlicht.zb.exampleproject.mvppractice.constract.AdapterContract
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class MvpPracticePresenterTest {
    @Mock
    private lateinit var view: MvpPracticeConstract.View

    @Mock
    private lateinit var adapterView: AdapterContract.View

    @Mock
    private lateinit var adapterModel: AdapterContract.Model

    lateinit var presenter: MvpPracticeConstract.Presenter

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        view = mock(MvpPracticeConstract.View::class.java)
        adapterView = mock(AdapterContract.View::class.java)
        adapterModel = mock(AdapterContract.Model::class.java)
        presenter = MvpPracticePresenter(view, adapterView, adapterModel)
    }

    @Test
    fun test_initialize_view() {
        verify(view).initView()
    }

    @Test
    fun test_load_github_data_success__exist_user_name() {
        `when`(view.getKeyword()).thenReturn("mdlicht")

        presenter.searchRepository(view.getKeyword())

        verify(view).showData()
        verify(view, never()).showEmpty()
        verify(adapterModel).setDataSet(ArgumentMatchers.anyList())
        verify(adapterView).notifyGitHubDataSetChanged()
    }

    @Test
    fun test_load_github_data_fail__wrong_user_name() {
        `when`(view.getKeyword()).thenReturn("!@#$%^^&*()_!@#$%^&*()")

        presenter.searchRepository(view.getKeyword())

        verify(view, never()).showData()
        verify(view).showEmpty()
        verify(adapterModel).setDataSet(null)
        verify(adapterView).notifyGitHubDataSetChanged()
    }

    @After
    fun finish() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}