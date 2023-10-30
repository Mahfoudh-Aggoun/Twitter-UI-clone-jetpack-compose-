package com.JetpackPractice.twitterui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.domain.model.PostModel
import com.JetpackPractice.twitterui.routing.Screen
import com.JetpackPractice.twitterui.viewModel.MainViewModel


private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)


@Composable
fun Post(
    universalNavHostController: NavHostController?,
    post: PostModel = PostModel.DEFAULT_POST,
    index: Int = 0
) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .padding(bottom = 5.dp, end = 5.dp)
    ) {
        val (profileImage, header, text, image, postActions, threeDots, divider) = createRefs()
        Image(
            painter = painterResource(id = post.profilePic),
            modifier = Modifier
                .padding(top = 4.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .size(40.dp)
                .constrainAs(profileImage) {
                    start.linkTo(parent.start)
                }
                .clickable(onClick = {
                    onProfileClick(universalNavHostController!!, index)}),
            contentScale = ContentScale.Fit,
            contentDescription = ""
        )
        PostHeader(modifier = Modifier
            .constrainAs(header) {
                start.linkTo(profileImage.end)
            }
            .padding(start = 7.dp),
            post = post)

        Text(
            modifier = Modifier
                .widthIn(max = (LocalConfiguration.current.screenWidthDp - 55).dp)
                .padding(start = 7.dp, top = 5.dp)
                .constrainAs(text) {
                    start.linkTo(profileImage.end)
                    top.linkTo(header.bottom)

                },
            text = post.text,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

        if (post.hasPic)
            Image(
                modifier = Modifier
                    .widthIn(max = (LocalConfiguration.current.screenWidthDp - 57).dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 7.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Blue)
                    .constrainAs(image) {
                        start.linkTo(profileImage.end)
                        if (post.hasText)
                            top.linkTo(text.bottom)
                        else
                            top.linkTo(header.bottom)
                    },
                painter = painterResource(id = post.image),
                contentDescription = "",
            )

        PostActions(postModel = post,
            modifier = Modifier
                .widthIn(max = (LocalConfiguration.current.screenWidthDp - 57).dp)
                .fillMaxWidth()
                .constrainAs(postActions) {
                    start.linkTo(profileImage.end)
                    if (post.hasPic)
                        top.linkTo(image.bottom)
                    else
                        top.linkTo(text.bottom)
                }
                .padding(start = 7.dp, top = 6.dp))
        Icon(
            modifier = Modifier
                .constrainAs(threeDots) {
                    centerVerticallyTo(header)
                    end.linkTo(parent.end)
                }
                .padding(end = 5.dp)
                .size(18.dp),
            painter = painterResource(id = R.drawable.three_dots),
            contentDescription = ""
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f),
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(postActions.bottom)
                }
                .padding(top = 5.dp)
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun PostHeader(
    post: PostModel = PostModel.DEFAULT_POST,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(20.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = post.username,
            modifier = Modifier.widthIn(0.dp, 150.dp),
            overflow = TextOverflow.Ellipsis,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        if(post.verified) {
            Icon(
                painter = painterResource(id = R.drawable.verified_badge),
                contentDescription = "verified badge",
                modifier = Modifier.size(10.dp).offset(y = 2.dp),
                tint = colorResource(id = R.color.twitterBlue)
            )
        }

        Text(
            text = post.accountname,
            fontFamily = chirpFamily,
            modifier = Modifier.widthIn(0.dp, 60.dp),
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = "· " + post.date,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            fontSize = 14.sp
        )

    }
}


@Composable
fun PostActions(postModel: PostModel, modifier: Modifier) {
    Row(
        modifier = modifier.height(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostAction(R.drawable.comments_icon, postModel.comments)
        PostAction(R.drawable.retweet_icon, postModel.retweets)
        PostAction(vectorResourceId = R.drawable.likes_icon, text = postModel.likes)
        PostAction(vectorResourceId = R.drawable.views_icon, text = postModel.views)
        PostAction(vectorResourceId = R.drawable.share_icon, text = -1)
    }
}


@Composable
fun PostAction(
    @DrawableRes vectorResourceId: Int,
    text: Int,
) {
    Box() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(id = vectorResourceId),
                contentDescription = "",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            if (text != -1)
                Text(
                    text = text.toString(),
                    fontWeight = FontWeight.Normal,
                    fontFamily = chirpFamily,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
        }
    }
}

fun onProfileClick(navController: NavHostController, index: Int) {
    navController.navigate(Screen.Profile.route+"/$index") {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}