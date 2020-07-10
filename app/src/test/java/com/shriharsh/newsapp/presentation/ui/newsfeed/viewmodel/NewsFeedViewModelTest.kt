package com.shriharsh.newsapp.presentation.ui.newsfeed.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shriharsh.newsapp.data.NewsDataRepository
import com.shriharsh.newsapp.data.toDomainModel
import com.shriharsh.newsapp.utils.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsFeedViewModelTest {

    @Mock
    private lateinit var mockNewsDataRepository: NewsDataRepository

    private lateinit var newsFeedViewModel: NewsFeedViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsFeedViewModel = NewsFeedViewModel(mockNewsDataRepository)
    }

    @Test
    fun `test when repository returns successful data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsDataRepository.fetchTopHeadlines()).thenReturn(
                Resource.Success(
                    getDummyNewsAPI().toDomainModel()
                )
            )

            val observerResult = newsFeedViewModel.observerFeeds()
            newsFeedViewModel.fetchTopHeadlines()
            verify(mockNewsDataRepository).fetchTopHeadlines()

            when (val observedValue = observerResult.getOrAwaitValue()) {
                is Resource.Success -> {
                    val result = observedValue.data.size
                    Assert.assertEquals(1, result)
                }
                is Resource.Failure -> {
                    Assert.assertTrue(false)
                }
                is Resource.Loading -> {
                    Assert.assertTrue(false)
                }
            }
        }

    @Test
    fun `test when repository returns failure and has no cached data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsDataRepository.fetchTopHeadlines()).thenReturn(
                Resource.Failure(
                    Exception("Something went wrong")
                )
            )

            whenever(mockNewsDataRepository.getCachedArticles()).thenReturn(listOf())

            val observerResult = newsFeedViewModel.observerFeeds()
            newsFeedViewModel.fetchTopHeadlines()
            verify(mockNewsDataRepository).fetchTopHeadlines()

            when (val observedValue = observerResult.getOrAwaitValue()) {
                is Resource.Success -> {
                    Assert.assertTrue(false)
                }
                is Resource.Failure -> {
                    val exception = observedValue.throwable
                    Assert.assertEquals("Something went wrong", exception.message)
                }
                is Resource.Loading -> {
                    Assert.assertTrue(false)
                }
            }
        }

    @Test
    fun `test when repository returns failure and has previously cached data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsDataRepository.fetchTopHeadlines()).thenReturn(
                Resource.Failure(
                    Exception("Something went wrong")
                )
            )

            whenever(mockNewsDataRepository.getCachedArticles()).thenReturn(getDummyArticles())

            val observerResult = newsFeedViewModel.observerFeeds()
            newsFeedViewModel.fetchTopHeadlines()
            verify(mockNewsDataRepository).fetchTopHeadlines()

            when (val observedValue = observerResult.getOrAwaitValue()) {
                is Resource.Success -> {
                    val result = observedValue.data.size
                    Assert.assertEquals(1, result)
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