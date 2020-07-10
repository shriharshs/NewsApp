package com.shriharsh.newsapp.data.remote.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shriharsh.newsapp.data.remote.network.NewsNetworkAPI
import com.shriharsh.newsapp.utils.CoroutinesTestRule
import com.shriharsh.newsapp.utils.Resource
import com.shriharsh.newsapp.utils.getDummyHTTPException
import com.shriharsh.newsapp.utils.getDummyNewsAPI
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsRemoteDataSourceTest {

    @Mock
    private lateinit var mockNetworkApi: NewsNetworkAPI

    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsRemoteDataSource = NewsRemoteDataSource(mockNetworkApi)
    }

    @Test
    fun `test when news network api returns success`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNetworkApi.fetchTopHeadlines()).thenReturn(getDummyNewsAPI())

            val response = newsRemoteDataSource.fetchTopHeadlines()
            verify(mockNetworkApi).fetchTopHeadlines()

            when (response) {
                is Resource.Success -> {
                    val status = response.data.status
                    assertEquals("ok", status)
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
    fun `test when news network api returns error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(mockNetworkApi.fetchTopHeadlines()).thenThrow(getDummyHTTPException())

            val expectedException = Exception("Please check Internet Connection")
            val response = newsRemoteDataSource.fetchTopHeadlines()
            verify(mockNetworkApi).fetchTopHeadlines()

            when (response) {
                is Resource.Success -> {
                    assertTrue(false)
                }
                is Resource.Failure -> {
                    val exception = response.throwable
                    assertEquals(expectedException.message, exception.message)
                }
                is Resource.Loading -> {
                    assertTrue(false)
                }
            }
        }
}