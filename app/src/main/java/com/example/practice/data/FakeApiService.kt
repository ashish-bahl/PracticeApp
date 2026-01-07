package com.example.practice.data

import com.example.practice.common.LoginCred

class FakeApiService {

    suspend fun login(loginCred: LoginCred): Boolean {
        return true
    }
}