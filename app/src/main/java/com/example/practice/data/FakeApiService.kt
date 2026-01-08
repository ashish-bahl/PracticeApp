package com.example.practice.data

import com.example.practice.common.LoginCred

class FakeApiService {

    fun login(loginCred: LoginCred): Boolean {
        return !loginCred.password.contains("wrong")
    }
}