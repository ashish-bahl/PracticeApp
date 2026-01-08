package com.example.practice.data

import com.example.practice.common.LoginCred
import com.example.practice.common.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(
    private val apiService: FakeApiService = FakeApiService(),
) {

    suspend fun sampleLogin(loginCred: LoginCred): Flow<ResultState> {
        return flow {
            emit(ResultState.Loading)
            delay(2000)
            val result = apiService.login(loginCred)

            if (result) {
                emit(ResultState.Success)
            } else {
                emit(ResultState.Failure("Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }

}