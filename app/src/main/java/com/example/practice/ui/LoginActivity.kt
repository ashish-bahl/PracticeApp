package com.example.practice.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.practice.common.LoginCred
import com.example.practice.common.Result
import com.example.practice.ui.theme.PracticeAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    var userName by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    val loginViewModel: LoginViewModel by viewModels()
                    val loginState by loginViewModel.loginState.collectAsState()

                    when (val state = loginState) {
                        is Result.Success -> {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, SearchActivity::class.java))
                        }

                        is Result.Failure -> {
                            Text("Failed: ${state.errorMessage}")
                        }

                        else -> {
                            //nothing here
                        }
                    }


                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        TextField(
                            value = userName,
                            onValueChange = { newText: String ->
                                userName = newText
                            },
                            label = { Text("UserName") }
                        )

                        TextField(
                            value = password,
                            onValueChange = { newText: String ->
                                password = newText
                            },
                            label = { Text("Password") }
                        )

                        Button(onClick = {
                            if (userName.isNotEmpty() && password.isNotEmpty()) {
                                loginViewModel.loginWithCredentials(
                                    LoginCred(
                                        userName,
                                        password
                                    )
                                )
                            }
                        }) {
                            Text("Submit")
                        }
                    }

                    if (loginState is Result.Loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeAppTheme {
        Greeting("Android")
    }
}