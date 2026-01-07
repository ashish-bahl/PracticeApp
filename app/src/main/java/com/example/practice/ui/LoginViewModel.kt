package com.example.practice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.common.LoginCred
import com.example.practice.common.Result
import com.example.practice.data.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val loginRepository: LoginRepository = LoginRepository()

    private val _loginState = MutableStateFlow<Result?>(null)
    val loginState: StateFlow<Result?> = _loginState.asStateFlow()

    fun loginWithCredentials(loginCred: LoginCred) {
        viewModelScope.launch {
            loginRepository.sampleLogin(loginCred).collectLatest {
                _loginState.value = it
            }
        }
    }
}