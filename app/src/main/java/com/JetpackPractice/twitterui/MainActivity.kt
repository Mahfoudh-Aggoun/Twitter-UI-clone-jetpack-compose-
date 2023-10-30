package com.JetpackPractice.twitterui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.JetpackPractice.twitterui.ui.theme.TwitterUITheme
import com.JetpackPractice.twitterui.viewModel.MainViewModel
import com.JetpackPractice.twitterui.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            this
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TwitterUI)

        super.onCreate(savedInstanceState)
        setContent {
            TwitterUiApp(viewModel)
        }
    }
}

