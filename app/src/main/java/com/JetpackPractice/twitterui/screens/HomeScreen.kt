package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.components.Post
import com.JetpackPractice.twitterui.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

private val tabNames = listOf("For you", "Following")
private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.SemiBold),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun HomeScreen(
    universalNavHostController: NavHostController,
    viewModel: MainViewModel = MainViewModel(),
    nestedScrollConnection: NestedScrollConnection,
    topBarHeight: Dp
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val posts = viewModel.getHomeScreenPosts()


    Column(Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            modifier = Modifier
                .height(topBarHeight)
                .shadow(1.dp),
            title = {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.White,
                    divider = {},
                    indicator = {
                        TabIndicator(
                            tabPosition = it,
                            index = pagerState.currentPage,
                            45
                        )
                    }
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
                                        15.sp
                                    else
                                        14.sp,
                                    text = nameResource,
                                    fontFamily = chirpFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = if (index == pagerState.currentPage)
                                        Color.Black
                                    else
                                        Color.Gray,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.White
            ),
        )
        Row(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .width(10.dp)
            ) {}
            CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(nestedScrollConnection),
                    count = tabNames.size,
                    state = pagerState,
                ) {

                    LazyColumn(Modifier.fillMaxSize()) {
                        items(posts.size) { index ->
                            Post(
                                universalNavHostController = universalNavHostController,
                                post = posts[index],
                                index = index
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabIndicator(tabPosition: List<TabPosition>, index: Int, padding: Int) {
    Box(
        modifier = Modifier
            .tabIndicatorOffset(tabPosition[index])
            .height(5.5.dp)
            .padding(start = padding.dp, end = padding.dp, bottom = 2.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color = colorResource(id = R.color.twitterBlue))
    )
}

