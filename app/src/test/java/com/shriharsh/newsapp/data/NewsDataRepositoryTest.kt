package com.shriharsh.newsapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shriharsh.newsapp.data.local.source.NewsLocalDataSource
import com.shriharsh.newsapp.data.remote.source.NewsRemoteDataSource
import com.shriharsh.newsapp.utils.CoroutinesTestRule
import com.shriharsh.newsapp.utils.Resource
import com.shriharsh.newsapp.utils.getDummyArticles
import com.shriharsh.newsapp.utils.getDummyNewsAPI
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsDataRepositoryTest {

    @Mock
    private lateinit var mockNewsRemoteDataSource: NewsRemoteDataSource

    @Mock
    private lateinit var mockNewsLocalDataSource: NewsLocalDataSource

    private lateinit var newsDataRepository: NewsDataRepository

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsDataRepository = NewsDataRepository(mockNewsRemoteDataSource, mockNewsLocalDataSource)
    }

    @Test
    fun `test when remote repository returns successful data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsRemoteDataSource.fetchTopHeadlines()).thenReturn(
                Resource.Success(
                    getDummyNewsAPI()
                )
            )

            val response = newsDataRepository.fetchTopHeadlines()
            verify(mockNewsRemoteDataSource).fetchTopHeadlines()

            when (response) {
                is Resource.Success -> {
                    val totalResults = response.data.totalResults
                    assertEquals(38, totalResults)
                }
                is Resource.Failure -> {
                    assertTrue(false)
                }
                is Resource.Loading -> {
                    assertTrue(false)
                }
            }
        }


    @Test
    fun `test when remote repository returns failure`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsRemoteDataSource.fetchTopHeadlines()).thenReturn(
                Resource.Failure(
                    Exception("Something went wrong")
                )
            )

            val response = newsDataRepository.fetchTopHeadlines()
            verify(mockNewsRemoteDataSource).fetchTopHeadlines()

            when (response) {
                is Resource.Success -> {
                    assertTrue(false)
                }
                is Resource.Failure -> {
                    val exception = response.throwable
                    assertEquals("Something went wrong", exception.message)
                }
                is Resource.Loading -> {
                    assertTrue(false)
                }
            }
        }

    @Test
    fun `test to get data from the local storage in case of failure`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNewsLocalDataSource.getAllArticles()).thenReturn(getDummyArticles())

            val cachedArticles = newsDataRepository.getCachedArticles()
            verify(mockNewsLocalDataSource).getAllArticles()

            assertEquals(1, cachedArticles.size)
        }

}
