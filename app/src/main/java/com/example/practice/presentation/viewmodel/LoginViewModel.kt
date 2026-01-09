package com.example.practice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.R
import com.example.practice.common.LoginCred
import com.example.practice.common.ResultState
import com.example.practice.data.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository = LoginRepository(),
): ViewModel() {

    private val _loginState = MutableStateFlow<ResultState?>(null)
    val loginState: StateFlow<ResultState?> = _loginState.asStateFlow()

    // Input validation states using Resource IDs
    private val _userNameError = MutableStateFlow<Int?>(null)
    val userNameError: StateFlow<Int?> = _userNameError.asStateFlow()

    private val _passwordError = MutableStateFlow<Int?>(null)
    val passwordError: StateFlow<Int?> = _passwordError.asStateFlow()

    fun loginWithCredentials(loginCred: LoginCred) {
        if (!validateCredentials(loginCred)) {
            return
        }
        viewModelScope.launch {
            loginRepository.sampleLogin(loginCred).collectLatest {
                _loginState.value = it
            }
        }
    }

    private fun validateCredentials(credentials: LoginCred): Boolean {
        var isValid = true

        if (credentials.userName.isBlank()) {
            _userNameError.value = R.string.error_username_required
            isValid = false
        } else if (credentials.userName.length < 6) {
            _userNameError.value = R.string.error_username_length
            isValid = false
        } else {
            _userNameError.value = null
        }

        if (credentials.password.isBlank()) {
            _passwordError.value = R.string.error_password_required
            isValid = false
        } else if (credentials.password.length < 6) {
            _passwordError.value = R.string.error_password_length
            isValid = false
        } else {
            _passwordError.value = null
        }

        return isValid
    }
}