package com.example.practice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun loginWithCredentials(loginCred: LoginCred) {
        viewModelScope.launch {
            loginRepository.sampleLogin(loginCred).collectLatest {
                _loginState.value = it
            }
        }
    }
}