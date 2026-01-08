package com.example.practice

import com.example.practice.common.LoginCred
import com.example.practice.common.ResultState
import com.example.practice.data.LoginRepository
import com.example.practice.ui.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: LoginRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state to Success`() = runTest {
        val creds = LoginCred("user", "pass")
        val expectedResult = ResultState.Success

        coEvery { repository.sampleLogin(creds) } returns flowOf(expectedResult)

        viewModel.loginWithCredentials(creds)
        testDispatcher.scheduler.advanceUntilIdle()

        Assert.assertEquals(expectedResult, viewModel.loginState.value)
    }

    @Test
    fun `login failure updates state to Failure`() = runTest {
        val creds = LoginCred("user", "wrong_pass")
        val expectedResult = ResultState.Failure("Wrong password")

        coEvery { repository.sampleLogin(creds) } returns flowOf(expectedResult)

        viewModel.loginWithCredentials(creds)
        testDispatcher.scheduler.advanceUntilIdle()

        Assert.assertEquals(expectedResult, viewModel.loginState.value)
    }
}