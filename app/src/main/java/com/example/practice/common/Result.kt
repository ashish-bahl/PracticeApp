package com.example.practice.common

sealed class Result {
    data object Success : Result()
    data class Failure(val errorMessage: String) : Result()
    data object Loading : Result()
}