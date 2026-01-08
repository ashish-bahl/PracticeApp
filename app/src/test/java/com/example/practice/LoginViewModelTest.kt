package com.example.practice

import com.example.practice.common.LoginCred
import com.example.practice.common.ResultState
import com.example.practice.data.LoginRepository
import com.example.practice.ui.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
        unmockkAll()
    }

    @Test
    fun `login success updates state to Success`() = runTest {
        val loginCred = LoginCred("validUser", "validPass")
        val expectedResult = ResultState.Success

        coEvery { repository.sampleLogin(loginCred) } returns flowOf(expectedResult)

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedResult, viewModel.loginState.value)
        assertNull(viewModel.userNameError.value)
        assertNull(viewModel.passwordError.value)
    }

    @Test
    fun `login failure updates state to Failure`() = runTest {
        val loginCred = LoginCred("validUser", "validPass")
        val expectedResult = ResultState.Failure("Wrong password")

        coEvery { repository.sampleLogin(loginCred) } returns flowOf(expectedResult)

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedResult, viewModel.loginState.value)
    }

    @Test
    fun `login with short username sets userNameError`() = runTest {
        val loginCred = LoginCred("short", "validPass")

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(R.string.error_username_length, viewModel.userNameError.value)
        assertNull(viewModel.loginState.value)
    }

    @Test
    fun `login with empty username sets userNameError`() = runTest {
        val loginCred = LoginCred("", "validPass")

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(R.string.error_username_required, viewModel.userNameError.value)
        assertNull(viewModel.loginState.value)
    }

    @Test
    fun `login with short password sets passwordError`() = runTest {
        val loginCred = LoginCred("validUser", "123")

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(R.string.error_password_length, viewModel.passwordError.value)
        assertNull(viewModel.loginState.value)
    }

    @Test
    fun `login with empty password sets passwordError`() = runTest {
        val loginCred = LoginCred("validUser", "")

        viewModel.loginWithCredentials(loginCred)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(R.string.error_password_required, viewModel.passwordError.value)
        assertNull(viewModel.loginState.value)
    }
}
