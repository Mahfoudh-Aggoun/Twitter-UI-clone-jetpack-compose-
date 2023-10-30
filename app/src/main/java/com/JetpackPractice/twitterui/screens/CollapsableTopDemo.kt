package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.JetpackPractice.twitterui.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//private val chirpFamily = FontFamily(
//    Font(R.font.worksans_light, FontWeight.Light),
//    Font(R.font.worksans_regular, FontWeight.Normal),
//    Font(R.font.worksans_medium, FontWeight.Medium),
//    Font(R.font.worksans_semibold, FontWeight.SemiBold),
//    Font(R.font.worksans_semibold, FontWeight.SemiBold)
//)

private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@Preview(showSystemUi = true)
@Composable
fun CollapsableTopDemo() {
    var collapsingTopHeight by remember { mutableStateOf(0) }
    var collapsingTopHeight2 by remember { mutableStateOf(0) }

    var offset by remember { mutableStateOf(0f) }

    fun calculateOffset(delta: Float): Offset {
        val oldOffset = offset
        val newOffset = (oldOffset + delta).coerceIn(
            (-collapsingTopHeight - collapsingTopHeight2 + 110).toFloat(),
            0f
        )
        offset = newOffset
        return Offset(0f, newOffset - oldOffset)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val off =
                    when {
                        available.y >= 0 -> Offset.Zero
                        offset == -collapsingTopHeight.toFloat() -> Offset.Zero
                        else -> calculateOffset(available.y)
                    }
                return off
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                val off =
                    when {
                        available.y <= 0 -> Offset.Zero
                        offset == 0f -> Offset.Zero
                        else -> calculateOffset(available.y)
                    }
                return off
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
    ) {
        Box(
            modifier = Modifier
                .onSizeChanged { size ->
                    collapsingTopHeight = size.height
                }
                .offset { IntOffset(x = 0, y = (offset + collapsingTopHeight2).roundToInt()) },
            content = { CollapsedHeaderContent() },
        )
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    x = 0,
                    y = (collapsingTopHeight + collapsingTopHeight2 + offset).roundToInt()
                )
            },
            content = { TabContent(offset = offset) },
        )


        Box(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .onSizeChanged { size ->
                collapsingTopHeight2 = size.height
            }
            .offset {
                IntOffset(
                    x = 0,
                    y = offset
                        .roundToInt()
                        .coerceIn(minimumValue = -130, maximumValue = 0)
                )
            },
            content = {

                Image(
                    painter = painterResource(id = R.drawable.dummy_cover_pic),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.dummy_cover_pic_blurred),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha((-0.008 * offset).toFloat()),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.black_image),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha((-0.004*offset).coerceIn(0.0, 0.5).toFloat()),
                    contentScale = ContentScale.Crop
                )

            })
        Box(modifier = Modifier
            .width(100.dp)
            .height(80.dp)
            .offset { IntOffset(x = 0, y = (offset + collapsingTopHeight2 - 57).roundToInt()) },
            contentAlignment = Alignment.BottomCenter,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.default_profile_pic),
                    contentDescription = "account",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Dp((0.3 * offset + 75).toFloat()))
                        .alpha(if ((0.3 * offset + 75) <= 45) 0f else 1f)
                        .border(BorderStroke(4.dp, Color.White), CircleShape)
                )
            })
    }
}


@Composable
fun CollapsedHeaderContent(
    modifier: Modifier = Modifier,
)  {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .background(Color.White)
            .offset(y = 4.dp)
    ) {
        val (profilePic, followButton, username, accountName, locationAndJoinedDate, bio, followersAndFollowing) = createRefs()

        Image(painter = painterResource(id = R.drawable.default_profile_pic),
            contentDescription = "account",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .size(Dp(45f))
                .border(BorderStroke(4.dp, Color.White), CircleShape)
                .constrainAs(profilePic) {
                    start.linkTo(parent.start, 27.dp)
                    top.linkTo(parent.top)
                }
        )

        Surface(
            modifier = Modifier
                .constrainAs(followButton) {
                    end.linkTo(parent.end, 10.dp)
                    top.linkTo(parent.top)
                }
                .offset(y = -2.dp),
            onClick = {},
            shape = RoundedCornerShape(50),
            color = Color.Black,
            contentColor = MaterialTheme.colors.primary,

            ) {
            Text(
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 25.dp),
                text = "Follow",
                color = Color.White,
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Text(modifier = Modifier.constrainAs(username){
            start.linkTo(parent.start, 10.dp)
            top.linkTo(profilePic.bottom, 8.dp)
        },
            text = "The Android Site",
            fontSize = 22.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold)

        Text(modifier = Modifier.constrainAs(accountName){
            start.linkTo(parent.start, 10.dp)
            top.linkTo(username.bottom, 2.dp)
        },
            color = Color.Gray,
            text = "@TheAndroidSite",
            fontSize = 14.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal)

        Text(modifier = Modifier.constrainAs(bio){
            start.linkTo(parent.start, 10.dp)
            top.linkTo(accountName.bottom, 5.dp)
        },
            text = "Android news, rumors, reviews, videos, how-to guides and editorials. Everything you need to know about Google's phone and tablet OS.",
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal)

        Row(modifier = Modifier
            .constrainAs(locationAndJoinedDate){
                start.linkTo(parent.start, 10.dp)
                top.linkTo(bio.bottom, 10.dp)
            }) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.location_on_24),
                contentDescription = "",
                tint = Color.Gray
            )
            Text(text = "Algeria",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 20.dp))
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.calendar_month_24),
                contentDescription = "",
                tint = Color.Gray
            )
            Text(text = "Joined July 2015",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 15.dp))
        }

        Row(modifier = Modifier
            .constrainAs(followersAndFollowing){
                start.linkTo(parent.start, 10.dp)
                top.linkTo(locationAndJoinedDate.bottom, 10.dp)
            }) {
            Text(text = "12",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Following",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 20.dp))
            Text(text = "272",
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Followers",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 15.dp))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(modifier: Modifier = Modifier, offset: Float) {
    val pagerState = rememberPagerState()
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, offset)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Home" to Icons.Default.Home,
        "Shopping" to Icons.Default.ShoppingCart,
        "Settings" to Icons.Default.Settings
    )

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.Blue
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            // on below line we are creating a tab.
            Tab(
                icon = {
                    Icon(
                        imageVector = list[index].second, contentDescription = null,
                        tint = if (pagerState.currentPage == index) Color.Blue else Color.Black
                    )
                },
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index) Color.Blue else Color.Black
                    )
                },
                selected = pagerState.currentPage == index,

                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, offset: Float) {
    HorizontalPager(state = pagerState, count = 3) { page ->
        when (page) {
            0 -> TabContentScreen(data = "Welcome to Home Screen", offset)
            1 -> TabContentScreen(data = "Welcome to Shopping Screen", offset)
            2 -> TabContentScreen(data = "Welcome to Settings Screen", offset)
        }
    }
}


@Composable
fun TabContentScreen(data: String, offset: Float) {
    val itemsList = (0..500).toList()
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = itemsList) { item ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "$data $item",
                style = MaterialTheme.typography.subtitle1,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}