package com.example.practice

import app.cash.turbine.test
import com.example.practice.data.SearchRepository
import com.example.practice.presentation.viewmodel.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: SearchRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        repository = mockk()
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `search with 3 or more characters returns results`() = runTest {
        val query = "Apple"
        val expectedResults = listOf("Apple", "App Store")

        coEvery { repository.performSearch(query) } returns expectedResults

        viewModel.searchResults.test {
            viewModel.searchTheQuery(query)

            // Advance time to bypass the 300ms debounce
            testDispatcher.scheduler.advanceTimeBy(301)

            val result = awaitItem()
            Assert.assertEquals(expectedResults, result)
        }
    }

    @Test
    fun `search with less than 3 characters returns null`() = runTest {
        viewModel.searchResults.test {
            viewModel.searchTheQuery("ab")

            testDispatcher.scheduler.advanceTimeBy(301)

            val result = awaitItem()
            Assert.assertNull(result)

            // Verify repository was NEVER called
            coVerify(exactly = 0) { repository.performSearch(any()) }
        }
    }

    @Test
    fun `multiple rapid queries are debounced`() = runTest {
        val finalQuery = "Android"
        coEvery { repository.performSearch(any()) } returns listOf("Result")

        viewModel.searchResults.test {
            viewModel.searchTheQuery("A")
            viewModel.searchTheQuery("An")
            viewModel.searchTheQuery("And")
            viewModel.searchTheQuery(finalQuery)

            testDispatcher.scheduler.advanceTimeBy(301)

            awaitItem()

            // Verify repository was called exactly ONCE with the last query
            coVerify(exactly = 1) { repository.performSearch(finalQuery) }
        }
    }
}
