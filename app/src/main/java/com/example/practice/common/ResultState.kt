package com.example.practice.common

sealed class ResultState {
    data object Success : ResultState()
    data class Failure(val errorMessage: String) : ResultState()
    data object Loading : ResultState()
}