package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.viewModel.MainViewModel

private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@Preview(showSystemUi = true)
@Composable
fun InboxScreen(viewModel: MainViewModel = MainViewModel()){
    Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(start = 25.dp, end = 40.dp)) {
        Text(modifier = Modifier.padding(bottom = 10.dp),
            text = "Welcome to your inbox!",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Text(
            text = "Drop a line, share posts and more with private conversations between you and others on X.",
            fontFamily = chirpFamily,
            color = Color.Gray,
            fontSize = 16.sp,
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(top = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color.Black),
            shape = RoundedCornerShape(20.dp),
        ) {
            androidx.compose.material3.Text(
                text = "Write a message",
                fontFamily = chirpFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}