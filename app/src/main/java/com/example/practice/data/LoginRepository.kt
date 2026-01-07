package com.example.practice.data

import com.example.practice.common.LoginCred
import com.example.practice.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository {
    private val apiService: FakeApiService = FakeApiService()

    suspend fun sampleLogin(loginCred: LoginCred): Flow<Result> {
        return flow {
            emit(Result.Loading)
            delay(2000)
            val success = apiService.login(loginCred)

            if (success) {
                emit(Result.Success)
            } else {
                emit(Result.Failure("Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }

}