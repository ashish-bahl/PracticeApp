package com.example.practice.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.practice.data.SearchRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchRepository: SearchRepository = SearchRepository()
) : ViewModel() {

    private val _searchQuery = MutableSharedFlow<String>()
    val searchResults = _searchQuery
        .debounce(300.milliseconds)
        .map { query ->
            if (query.length >= 3) {
                searchRepository.performSearch(query)
            } else null
        }

    suspend fun searchTheQuery(query: String) {
        _searchQuery.emit(query)
    }
}