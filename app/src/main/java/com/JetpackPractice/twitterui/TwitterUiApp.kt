package com.JetpackPractice.twitterui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.JetpackPractice.twitterui.appDrawer.AppDrawer
import com.JetpackPractice.twitterui.domain.model.AccountModel
import com.JetpackPractice.twitterui.routing.Screen
import com.JetpackPractice.twitterui.screens.HomeScreen
import com.JetpackPractice.twitterui.screens.InboxScreen
import com.JetpackPractice.twitterui.screens.NotificationsScreen
import com.JetpackPractice.twitterui.screens.ProfileScreen
import com.JetpackPractice.twitterui.screens.SearchScreen
import com.JetpackPractice.twitterui.ui.theme.TwitterUITheme
import com.JetpackPractice.twitterui.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@Preview(showSystemUi = true)
@Composable
fun TwitterUiApp(viewModel: MainViewModel = MainViewModel()) {
    val universalNavController = rememberNavController()
    TwitterUITheme {
        NavHost(
            navController = universalNavController,
            startDestination = "main screen"
        ) {
            composable("main screen") {
                AppContent(viewModel, universalNavController)
            }
            composable(route = Screen.Profile.route + "/{index}",
            arguments = listOf(
                navArgument("index"){
                    type = NavType.IntType
                }
            )
            ) {
                val index = it.arguments?.getInt("index") ?: 0
                ProfileScreen(viewModel = viewModel,
                    onBackSelected = {universalNavController.popBackStack()},
                    index = index
                )
            }
        }
    }
}


@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@Composable
fun AppContent(viewModel: MainViewModel, universalNavHostController: NavHostController) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    var offset by remember { mutableStateOf(0f) }

    val transition = updateTransition(
        targetState = offset, label = "bottomBarTransition",
    )
    val bottomBarHeight
            by transition.animateDp(
                transitionSpec = { tween(400) },
                label = "Button Background Color"
            ) { state ->
                when {
                    state == 0.0f -> 55.dp
                    state <= -100.0f -> 0.dp
                    else -> {
                        55.dp
                    }
                }
            }

    val topBarHeight
            by transition.animateDp(
                transitionSpec = { tween(500) },
                label = "Button Background Color"
            ) { state ->
                when {
                    state == 0.0f -> 50.dp
                    state <= -100.0f -> 0.dp
                    else -> {
                        50.dp
                    }
                }
            }

    fun calculateOffset(delta: Float): Offset {
        val oldOffset = offset
        val newOffset = (oldOffset + delta).coerceIn(-100f, 0f)
        offset = newOffset
        return Offset(0f, newOffset - oldOffset)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val off =
                    when {
                        available.y >= 0 -> calculateOffset(available.y)

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
                        available.y <= 0 -> calculateOffset(available.y)
                        offset == 0f -> Offset.Zero
                        else -> calculateOffset(available.y)
                    }
                return off
            }
        }
    }


    val navBackStackEntry by navController.currentBackStackEntryAsState()


    Scaffold(
        topBar = getTopAppBar(
            topBarHeight,
            scaffoldState,
            coroutineScope,
            route = navBackStackEntry?.destination?.route
        ),
        drawerContent = { AppDrawer() },
        scaffoldState = scaffoldState,
        content = {
            MainScreenContainer(
                universalNavHostController = universalNavHostController,
                navController = navController,
                viewModel = viewModel,
                nestedScrollConnection,
                topBarHeight
            )
        },
        bottomBar = {
            BottomNavigationComponent(navController, bottomBarHeight)
        },

        floatingActionButton = {

            FloatingActionButton(modifier = Modifier.size(
                bottomBarHeight
            ),
                onClick = { /*TODO*/ },
            content = { Icon(painter = painterResource(id = R.drawable.add), contentDescription = "", tint = Color.White)},
            backgroundColor = colorResource(id = R.color.twitterBlue))

        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
fun getTopAppBar(
    topBarHeight: Dp,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    route: String?
): @Composable (() -> Unit) {
    return {
        when (route) {
            Screen.Home.route -> {
                TopAppBar(
                    topBarHeight = topBarHeight,
                    scaffoldState = scaffoldState,
                    coroutineScope = coroutineScope,
                    title = {
                        Box(modifier = Modifier.padding(top = Dp(7f))) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = "topBar app logo",
                                modifier = Modifier
                                    .size(Dp(25f))
                            )
                        }
                    },
                    actionIconId = null,
                    showActions = false,
                    shadow = 0
                )
            }

            Screen.Search.route -> {
                TopAppBar(
                    topBarHeight = topBarHeight,
                    scaffoldState = scaffoldState,
                    coroutineScope = coroutineScope,
                    title = {
                        SearchBar(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 0.dp)
                                .padding(start = 10.dp, end = 15.dp, top = 7.dp, bottom = 6.dp)
                                .clip(CircleShape)
                                .border(
                                    border = BorderStroke(1.dp, Color.Gray),
                                    shape = CircleShape
                                )
                                .offset(y = -4.dp),
                            colors = SearchBarDefaults.colors(colorResource(id = R.color.searchBar)),
                            query = "",
                            onQueryChange = {},
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            placeholder = {
                                Text(
                                    modifier = Modifier.offset(y = (-4).dp),
                                    text = "Search X",
                                    color = Color.Gray,
                                    overflow = TextOverflow.Visible
                                )
                            }
                        ) {
                        }
                    },
                    actionIconId = R.drawable.cog_outline,
                    showActions = true,
                    shadow = 1
                )

            }

            Screen.Notifications.route -> {
                TopAppBar(
                    topBarHeight = topBarHeight,
                    scaffoldState = scaffoldState,
                    coroutineScope = coroutineScope,
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, start = 5.dp),
                            text = "Notifications",
                            textAlign = TextAlign.Start,
                            fontFamily = chirpFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp
                        )
                    },
                    actionIconId = R.drawable.cog_outline,
                    showActions = true,
                    shadow = 1
                )
            }

            Screen.Inbox.route -> {
                TopAppBar(
                    topBarHeight = topBarHeight,
                    scaffoldState = scaffoldState,
                    coroutineScope = coroutineScope,
                    title = {
                        SearchBar(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 0.dp)
                                .padding(start = 10.dp, end = 15.dp, top = 7.dp, bottom = 6.dp)
                                .clip(CircleShape)
                                .border(
                                    border = BorderStroke(1.dp, Color.Gray),
                                    shape = CircleShape
                                )
                                .offset(y = -4.dp),
                            colors = SearchBarDefaults.colors(colorResource(id = R.color.searchBar)),
                            query = "",
                            onQueryChange = {},
                            onSearch = {},
                            active = false,
                            onActiveChange = {},
                            placeholder = {
                                Text(
                                    modifier = Modifier.offset(y = (-4).dp),
                                    text = "Search Direct Messages",
                                    color = Color.Gray,
                                    overflow = TextOverflow.Visible
                                )
                            }
                        ) {
                        }
                    },
                    actionIconId = R.drawable.cog_outline,
                    showActions = true,
                    shadow = 1
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun TopAppBar(
    topBarHeight: Dp,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    title: @Composable (() -> Unit),
    actionIconId: Int?,
    showActions: Boolean,
    shadow: Int
) {

    CenterAlignedTopAppBar(
        modifier = Modifier
            .height(topBarHeight)
            .shadow(shadow.dp),
        actions = {
            if (showActions)
                Icon(
                    modifier = Modifier.padding(top = 13.dp, end = 5.dp),
                    painter = painterResource(id = actionIconId!!),
                    contentDescription = ""
                )
            else {
            }
        },
        title = title,
        navigationIcon = {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                Image(painter = painterResource(id = R.drawable.default_profile_pic),
                    contentDescription = "account",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(Dp(35f))
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White
        ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationComponent(navController: NavHostController, height: Dp) {

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        NavigationItem(0, R.drawable.ic_home, R.drawable.ic_home_selected, "home", Screen.Home),
        NavigationItem(
            1,
            R.drawable.ic_search,
            R.drawable.ic_search_selected,
            "search",
            Screen.Search
        ),
        NavigationItem(
            2,
            R.drawable.ic_notifications,
            R.drawable.ic_notifications_selected,
            "notifications",
            Screen.Notifications
        ),
        NavigationItem(
            3,
            R.drawable.ic_messages,
            R.drawable.ic_messages_selected,
            "messages",
            Screen.Inbox
        )
    )

    BottomNavigation(
        modifier = Modifier.height(
            height
        ),
        backgroundColor = Color.White,
    ) {
        items.forEach {
            BottomNavigationItem(

                icon = {
                    Icon(
                        modifier = Modifier.size(Dp(25f)),
                        imageVector =
                        if (selectedItem == it.index) {
                            ImageVector.vectorResource(id = it.selectedVectorResourceId)
                        } else {
                            ImageVector.vectorResource(id = it.unselectedVectorResourceId)
                        },
                        contentDescription = it.contentDescriptionResourceId,
                        tint = Color.Black
                    )
                },
                selected = selectedItem == it.index,
                onClick = {
                    selectedItem = it.index
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },

                selectedContentColor = Color.Gray,

                )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContainer(
    universalNavHostController: NavHostController,
    navController: NavHostController,
    viewModel: MainViewModel,
    nestedScrollConnection: NestedScrollConnection,
    topBarHeight: Dp
) {
    Surface(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        color = MaterialTheme.colors.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    universalNavHostController = universalNavHostController,
                    nestedScrollConnection = nestedScrollConnection,
                    topBarHeight = topBarHeight
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    nestedScrollConnection = nestedScrollConnection,
                )
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
            composable(Screen.Inbox.route) {
                InboxScreen()
            }

        }
    }
}

private data class NavigationItem(
    val index: Int,
    val unselectedVectorResourceId: Int,
    val selectedVectorResourceId: Int,
    val contentDescriptionResourceId: String,
    val screen: Screen
)













