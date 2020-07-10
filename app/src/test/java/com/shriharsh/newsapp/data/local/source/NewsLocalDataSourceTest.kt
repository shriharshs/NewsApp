package com.shriharsh.newsapp.data.local.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shriharsh.newsapp.data.local.model.ArticleDao
import com.shriharsh.newsapp.utils.CoroutinesTestRule
import com.shriharsh.newsapp.utils.getDummyArticles
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsLocalDataSourceTest {

    @Mock
    private lateinit var mockArticleDao: ArticleDao

    private lateinit var newsLocalDataSource: NewsLocalDataSource

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsLocalDataSource = NewsLocalDataSource(mockArticleDao)
    }

    @Test
    fun `test to save articles`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val localDate = newsLocalDataSource.saveAllArticles(listOf())
            verify(mockArticleDao, times(1)).saveArticles(listOf())
        }


    @Test
    fun `test get all saved articles`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockArticleDao.getAllArticles()).thenReturn(getDummyArticles())

            val localDate = newsLocalDataSource.getAllArticles()
            verify(mockArticleDao).getAllArticles()

            Assert.assertTrue(localDate.size == 1)
        }
}