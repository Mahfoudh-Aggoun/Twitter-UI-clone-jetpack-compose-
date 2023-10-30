package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private val tabNames = listOf("All", "Verified", "Mentions")
private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.SemiBold),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
//@Preview(showSystemUi = true)
@Composable
fun NotificationsScreen(viewModel: MainViewModel = MainViewModel()) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(1.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            divider = {},
            indicator = { TabIndicator(tabPosition = it, index = pagerState.currentPage, 20) }
        ) {
            tabNames.forEachIndexed { index, nameResource ->
                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            fontSize = if (index == pagerState.currentPage)
                                16.sp
                            else
                                15.sp,
                            text = nameResource,
                            fontFamily = chirpFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = if (index == pagerState.currentPage)
                                Color.Black
                            else
                                Color.Gray
                        )
                    }
                )
            }
        }

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                count = tabNames.size,
                state = pagerState,
            ) {page ->
                when(page){
                    0-> AllScreen()
                    1-> VerifiedScreen()
                    2-> MentionsScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AllScreen() {
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.padding(start = 25.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(27.dp),
                painter = painterResource(id = R.drawable.sparkle),
                contentDescription = ""
            )
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.AccountCircle,
                    modifier = Modifier.size(Dp(37f)),
                    tint = Color.LightGray,
                    contentDescription = "account"
                )
            }
        }
        Text(
            modifier = Modifier.padding(start = 60.dp),
            text = "Recent post from jetpack compose",
            fontFamily = chirpFamily,
            fontSize = 15.sp,
        )
        Text(
            modifier = Modifier.padding(top = 5.dp, start = 60.dp, end = 20.dp),
            text = "how you can integrate jetpack compose " +
                    "in your projects gradually with xml views and how to apply testing",
            fontFamily = chirpFamily,
            color = Color.Gray,
            fontSize = 15.sp,
        )
        Divider(Modifier.padding(top = 10.dp),
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f))
    }
}

//@Preview(showSystemUi = true)
@Composable
fun VerifiedScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 40.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Nothing to see here --- yet",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Text(
            text = "Likes, mentions, reposts, and a while lot more, when it comes from a " +
                    "verified account, you'll find it here. \n \n Not verified? " +
                    "Subscribe now to get a verified account and join other people in quality" +
                    " conversations.",
            fontFamily = chirpFamily,
            color = Color.Gray,
            fontSize = 16.sp,
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.outlinedButtonColors(Color.Black),
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = "Subscribe",
                fontFamily = chirpFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            text = "IDR 165,000/month",
            fontFamily = chirpFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

//@Preview(showSystemUi = true)
@Composable
fun MentionsScreen(){
    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 40.dp)) {
        Text(modifier = Modifier.padding(bottom = 10.dp),
            text = "Join the conversation",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        Text(
            text = "When someone on X mentions you in a post or reply, you'll find it here.",
            fontFamily = chirpFamily,
            color = Color.Gray,
            fontSize = 16.sp,
        )
    }
}

