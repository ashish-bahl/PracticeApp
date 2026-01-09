package com.example.practice.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practice.data.SearchRepository
import com.example.practice.presentation.theme.PracticeAppTheme
import com.example.practice.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val searchViewModel: SearchViewModel by viewModels()
                    SearchScreen(Modifier.padding(innerPadding), searchViewModel)
                }
            }
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier, searchViewModel: SearchViewModel) {
    var query by rememberSaveable { mutableStateOf("") }

    val searchResults by searchViewModel.searchResults.collectAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = query,
            onValueChange = { newText: String ->
                query = newText
                coroutineScope.launch {
                    searchViewModel.searchTheQuery(query)
                }
            },
            label = { Text("Search here") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        )

        searchResults?.let {
            LazyColumn {
                items(it) { result ->
                    Text(text = result)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    PracticeAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(Modifier.padding(innerPadding), SearchViewModel(SearchRepository()))
        }
    }
}
