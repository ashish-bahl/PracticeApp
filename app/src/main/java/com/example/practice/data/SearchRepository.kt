package com.example.practice.data

class SearchRepository(
    private val apiService: FakeApiService = FakeApiService()
) {

    suspend fun performSearch(query: String): List<String> {
        return apiService.search(query)
    }

}
