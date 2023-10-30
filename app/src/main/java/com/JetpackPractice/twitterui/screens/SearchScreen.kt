package com.JetpackPractice.twitterui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.components.Post
import com.JetpackPractice.twitterui.domain.model.PostModel
import com.JetpackPractice.twitterui.viewModel.MainViewModel

private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

private val trendsItemList = listOf<TrendsItemData>(
        TrendsItemData("Business & finance", "musk",  "290K"),
        TrendsItemData("Fitness", "Fight IQ",  "322K"),
        TrendsItemData("Sports", "Jordan",  "99K"),
        TrendsItemData("Politics", "The AEC",  "10.3K"),
        TrendsItemData("Technology", "Jetpack Compose",  "290K")
)

@Composable
fun SearchScreen(viewModel: MainViewModel = MainViewModel(),
                 nestedScrollConnection: NestedScrollConnection,
){
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .nestedScroll(nestedScrollConnection)
    ) {
        Text(modifier = Modifier.padding(start = 15.dp, top = 10.dp),
            text = "Trends for you",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        for(item in trendsItemList){
            trendsItem(trendsItemData = item)
        }
        Text(modifier = Modifier.padding(start = 15.dp, top = 25.dp, bottom = 18.dp),
            text = "Show more",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = colorResource(id = R.color.twitterBlue)
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f)
        )
        for(item in viewModel.getSearchScreenPosts()) {
            TrendsPostsItem(type = item.topicName, posts =  item.posts)
        }
    }
}

@Composable
fun trendsItem(trendsItemData: TrendsItemData){
        Column(modifier = Modifier.padding(top = 20.dp, start = 15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = trendsItemData.trendType + " . Trending",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Gray)
            Text(text = trendsItemData.trend,
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(text = trendsItemData.numOfPosts + " posts",
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray)
        }
}

@Composable
fun TrendsPostsItem(type : String = "Technology", posts: List<PostModel>){
    Column(Modifier.padding(start = 10.dp, top = 10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 10.dp)){
            Image(painter = painterResource(id = R.drawable.trends_icon)
                , contentDescription = "",
            )

            Text(modifier = Modifier.padding(start = 15.dp),
                text = type,
                fontFamily = chirpFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        for(post in posts)
        Post(universalNavHostController = null, post = post);

        Text(modifier = Modifier.padding(start = 15.dp, top = 5.dp, bottom = 10.dp),
            text = "Show more",
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = colorResource(id = R.color.twitterBlue)
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f)
        )
    }
}

data class TrendsItemData(
    val trendType : String,
    val trend : String,
    val numOfPosts: String
)