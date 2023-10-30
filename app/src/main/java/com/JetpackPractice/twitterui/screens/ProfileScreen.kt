package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.components.Post
import com.JetpackPractice.twitterui.domain.model.AccountModel
import com.JetpackPractice.twitterui.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

private val tabNames = listOf("Posts", "Replies", "Media", "Likes")


@Composable
fun ProfileScreen(
    onBackSelected: () -> Unit = {},
    viewModel: MainViewModel,
    index: Int
) {
    val accountData : AccountModel = viewModel.getAccountData(index)
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
            content = { CollapsedHeaderContents(accountData = accountData) },
        )
        Box(
            modifier = Modifier.offset {
                IntOffset(
                    x = 0,
                    y = (collapsingTopHeight + collapsingTopHeight2 + offset).roundToInt()
                )
            },
            content = { TabsWithPager(accountData) },
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
                    painter = painterResource(id = accountData.coverPic),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = accountData.blurredCoverPic),
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
                        .alpha(
                            (-0.004 * offset)
                                .coerceIn(0.0, 0.5)
                                .toFloat()
                        ),
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
                    painter = painterResource(id = accountData.profilePic),
                    contentDescription = "account",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Dp((0.3 * offset + 75).toFloat()))
                        .alpha(if ((0.3 * offset + 75) <= 45) 0f else 1f)
                        .border(BorderStroke(4.dp, Color.White), CircleShape)
                )
            })

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (backBtn, userName, numberOfPosts, followBtn, searchBtn, menuBtn) = createRefs()

            Image(painter = painterResource(id = R.drawable.arrow_back_24),
                contentDescription = "account",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.transparentBlack))
                    .padding(4.dp)
                    .size(Dp(25f))
                    .constrainAs(backBtn) {
                        start.linkTo(parent.start, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    }
                    .clickable { onBackSelected.invoke() }
            )

            Image(painter = painterResource(id = R.drawable.three_dots_white_24),
                contentDescription = "account",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.transparentBlack))
                    .padding(2.dp)
                    .size(Dp(27f))
                    .constrainAs(menuBtn) {
                        end.linkTo(parent.end, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                    }
            )

            Image(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "account",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.transparentBlack))
                    .padding(5.dp)
                    .size(Dp(22f))
                    .constrainAs(searchBtn) {
                        end.linkTo(menuBtn.start, 15.dp)
                        top.linkTo(parent.top, 10.dp)
                    }
            )

            Text(
                text = accountData.username,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(userName) {
                        start.linkTo(backBtn.end, 25.dp)
                        top.linkTo(parent.top, 5.dp)
                    }
                    .alpha(
                        if (offset <= -287)
                            1f
                        else
                            0f
                    )
                    .widthIn(0.dp, 180.dp),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 21.sp,
                color = Color.White
            )

            Text(
                text = "1,510 posts",
                modifier = Modifier
                    .constrainAs(numberOfPosts) {
                        start.linkTo(backBtn.end, 25.dp)
                        top.linkTo(userName.bottom)
                    }
                    .alpha(
                        if (offset <= -287)
                            1f
                        else
                            0f
                    ),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.White
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .constrainAs(followBtn) {
                        end.linkTo(parent.end, 10.dp)
                        top.linkTo(parent.top, 5.dp)
                    }
                    .alpha(
                        if (offset <= -287)
                            1f
                        else
                            0f
                    ),
                colors = ButtonDefaults.outlinedButtonColors(Color.White),
                shape = RoundedCornerShape(20.dp),
            ) {
                Text(
                    text = "Follow",
                    fontFamily = chirpFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

//@Preview(showSystemUi = true)
@Composable
fun CollapsedHeaderContents(
    modifier: Modifier = Modifier,
    accountData: AccountModel) {
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .background(Color.White)
            .offset(y = 4.dp)
    ) {
        val (profilePic, followButton, username, accountName, locationAndJoinedDate, bio, followersAndFollowing) = createRefs()

        Image(painter = painterResource(id = accountData.profilePic),
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

        Text(
            modifier = Modifier
                .constrainAs(username) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(profilePic.bottom, 8.dp)
                }
                .widthIn(0.dp, 230.dp),
            text = accountData.username,
            fontSize = 22.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.constrainAs(accountName) {
                start.linkTo(parent.start, 10.dp)
                top.linkTo(username.bottom, 2.dp)
            },
            color = Color.Gray,
            text = accountData.accountname,
            fontSize = 14.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal
        )

        Text(
            modifier = Modifier.constrainAs(bio) {
                start.linkTo(parent.start, 10.dp)
                top.linkTo(accountName.bottom, 5.dp)
            },
            text = accountData.bio,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal
        )

        Row(
            modifier = Modifier
                .constrainAs(locationAndJoinedDate) {
                    start.linkTo(parent.start, 10.dp)
                    top.linkTo(bio.bottom, 10.dp)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.location_on_24),
                contentDescription = "",
                tint = Color.Gray
            )
            Text(
                text = accountData.location,
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 20.dp)
            )
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.calendar_month_24),
                contentDescription = "",
                tint = Color.Gray
            )
            Text(
                text = accountData.dateJoined,
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 15.dp)
            )
        }

        Row(modifier = Modifier
            .padding(bottom = 5.dp)
            .constrainAs(followersAndFollowing) {
                start.linkTo(parent.start, 10.dp)
                top.linkTo(locationAndJoinedDate.bottom, 10.dp)
            }) {
            Text(
                text = accountData.following,
                modifier = Modifier
                    .padding(end = 5.dp),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Following",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = accountData.followers,
                modifier = Modifier
                    .padding(end = 5.dp),
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Followers",
                color = Color.Gray,
                fontFamily = chirpFamily,
                modifier = Modifier.padding(end = 15.dp)
            )
        }
    }
}

//@Preview(showSystemUi = true)
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun TabsWithPager(accountData : AccountModel) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
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
                                15.sp
                            else
                                14.sp,
                            text = nameResource,
                            fontFamily = chirpFamily,
                            fontWeight = FontWeight.Bold,
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
                    .fillMaxSize()
                    .padding(start = 10.dp),
                count = tabNames.size,
                state = pagerState,
            ) {

                Column(Modifier.verticalScroll(rememberScrollState())) {
                    for (post in accountData.posts!!) {
                        Post(post = post, universalNavHostController = null)
                    }
                }
            }
        }

    }
}


