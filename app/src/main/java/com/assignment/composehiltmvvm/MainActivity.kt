package com.assignment.composehiltmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.assignment.composehiltmvvm.data.UserState
import com.assignment.composehiltmvvm.presentation.UserScreen
import com.assignment.composehiltmvvm.presentation.UserViewModel
import com.assignment.composehiltmvvm.ui.theme.ComposeHiltMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHiltMVVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(userViewModel = hiltViewModel<UserViewModel>())
                }
            }
        }
    }
}

@Composable
fun Greeting(userViewModel: UserViewModel) {

    LaunchedEffect(Unit, block = {
        userViewModel.getUser()
    })

    val userState by userViewModel.userState.collectAsState()

    UserScreen(userState, onListClick = {

    })

}


