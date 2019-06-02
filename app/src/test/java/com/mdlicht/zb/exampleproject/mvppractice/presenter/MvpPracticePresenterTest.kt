package com.mdlicht.zb.exampleproject.mvppractice.presenter

import com.mdlicht.zb.exampleproject.mvppractice.constract.AdapterContract
import com.mdlicht.zb.exampleproject.mvppractice.constract.MvpPracticeConstract
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubData
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubProfile
import com.mdlicht.zb.exampleproject.mvppractice.model.GitHubRepo
import com.mdlicht.zb.exampleproject.mvppractice.model.repository.GitHubCallback
import com.mdlicht.zb.exampleproject.mvppractice.model.repository.GitHubRepository
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.firstValue
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import java.lang.Exception

class MvpPracticePresenterTest {
    @Mock
    private lateinit var view: MvpPracticeConstract.View

    @Mock
    private lateinit var adapterView: AdapterContract.View

    @Mock
    private lateinit var adapterModel: AdapterContract.Model

    @Mock
    private lateinit var repository: GitHubRepository

    @Captor
    private lateinit var listener: ArgumentCaptor<GitHubCallback>

    lateinit var presenter: MvpPracticeConstract.Presenter

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        presenter = MvpPracticePresenter(view, adapterView, adapterModel, repository)
    }

    @Test
    fun test_initialize_view() {
        verify(view).initView()
    }

    @Test
    fun test_call_repository() {
        `when`(view.getKeyword()).thenReturn("mdlicht")
        presenter.searchRepository(view.getKeyword())
        verify(repository).searchRepository(eq(view.getKeyword()) ?: "mdlicht", capture(listener))
    }

    @Test
    fun test_load_github_data_success__exist_user_name() {
        val github = listOf<GitHubData>(
            GitHubProfile("mdlicht", "", ""),
            GitHubRepo("ColorDiary", "", 0),
            GitHubRepo("ExampleProject", "", 1)
        )

        `when`(view.getKeyword()).thenReturn("mdlicht")

        presenter.searchRepository(view.getKeyword())

        val keyword: String = view.getKeyword()
        verify(repository).searchRepository(eq(keyword) ?: "mdlicht", capture(listener))
        listener.firstValue.onLoadComplete(github)

        verify(view).showData()
        verify(view, never()).showEmpty()
        verify(adapterModel).setDataSet(anyList())
        verify(adapterModel).setDataSet(eq(github))
        verify(adapterView).notifyGitHubDataSetChanged()
    }

    @Test
    fun test_load_github_data_fail__wrong_user_name() {
        `when`(view.getKeyword()).thenReturn("!@#$%^^&*()_!@#$%^&*()")

        presenter.searchRepository(view.getKeyword())

        val keyword: String = view.getKeyword()
        verify(repository).searchRepository(eq(keyword) ?: "mdlicht", capture(listener))
        listener.firstValue.onLoadFail(Exception())

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