package com.shriharsh.newsapp.presentation.ui.newsdetail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shriharsh.newsapp.utils.CoroutinesTestRule
import com.shriharsh.newsapp.utils.Resource
import com.shriharsh.newsapp.utils.getDummyArticle
import com.shriharsh.newsapp.utils.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class NewsDetailViewModelTest {

    private lateinit var newsDetailViewModel: NewsDetailViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsDetailViewModel = NewsDetailViewModel()
    }

    @Test
    fun `test when article data is set from the intent`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val observerResult = newsDetailViewModel.observeArticleData()
            newsDetailViewModel.setArticleData(getDummyArticle())

            when (val observedValue = observerResult.getOrAwaitValue()) {
                is Resource.Success -> {
                    val result = observedValue.data
                    Assert.assertEquals("abc", result.title)
                }
                is Resource.Failure -> {
                    Assert.assertTrue(false)
                }
                is Resource.Loading -> {
                    Assert.assertTrue(false)
                }
            }
        }
}