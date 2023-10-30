package com.JetpackPractice.twitterui.appDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.JetpackPractice.twitterui.R

private val sectionDataList = listOf<SectionData>(
    SectionData("Proffessional Tools", listOf()),
    SectionData("Settings & Support", listOf("Settings and privacy", "Help Center"))
)
private val chirpFamily = FontFamily(
    Font(R.font.chirp_regular_web, FontWeight.Normal),
    Font(R.font.chirp_medium_web, FontWeight.Medium),
    Font(R.font.chirp_bold_web, FontWeight.Bold),
)

@Preview(showSystemUi = true)
@Composable
fun AppDrawer() {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        AppDrawerHeader()
        AppDrawerBody()
    }
}


@Composable
fun AppDrawerHeader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        val (profilePic, secondaryProfilePic, accounts, profileName, accountName, followersText, followersNum, divider) = createRefs()

        Image(painter = painterResource(id = R.drawable.default_profile_pic),
            contentDescription = "account",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .size(Dp(45f))
                .constrainAs(profilePic) {}
        )
        Image(imageVector = Icons.Filled.AccountCircle,
            colorFilter = ColorFilter.tint(Color.LightGray),
            contentDescription = "account",
            modifier = Modifier
                .size(Dp(30f))
                .constrainAs(secondaryProfilePic) {
                    centerVerticallyTo(profilePic)
                    end.linkTo(parent.end, Dp(55f))
                }
        )
        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.dots_vertical_circle_outline),
            contentDescription = "account",
            modifier = Modifier
                .size(Dp(27f))
                .constrainAs(accounts) {
                    centerVerticallyTo(profilePic)
                    end.linkTo(parent.end)
                }
        )
        Text(text = "mahfoudh aggoun",
            modifier = Modifier
                .constrainAs(profileName) {
                    top.linkTo(profilePic.bottom, Dp(10f))
                }
                .padding(start = 4.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = chirpFamily
        )
        Text(text = "@Mahfoudh_Aggn",
            modifier = Modifier
                .constrainAs(accountName) {
                    top.linkTo(profileName.bottom)
                }
                .padding(start = 4.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = chirpFamily,
            color = Color.Gray
        )
        Text(
            text = "86                  1",
            modifier = Modifier
                .constrainAs(followersNum) {
                    top.linkTo(accountName.bottom, Dp(10f))
                }
                .padding(start = 4.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = chirpFamily,
        )
        Text(text = "     Following    Follower",
            modifier = Modifier
                .constrainAs(followersText) {
                    top.linkTo(accountName.bottom, Dp(10f))
                }
                .padding(start = 4.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = chirpFamily,
            color = Color.Gray
        )
        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f),
            modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(followersNum.bottom)
                }
                .padding(
                    start = 4.dp,
                    top = 28.dp
                )
        )
    }
}

@Composable
fun AppDrawerBody() {
    val listItems = listOf<AppDrawerActionListItemData>(
        AppDrawerActionListItemData(R.drawable.account_outline, "Profile"),
        AppDrawerActionListItemData(R.drawable.logo, "Premium"),
        AppDrawerActionListItemData(R.drawable.bookmark_outline, "Bookmarks"),
        AppDrawerActionListItemData(R.drawable.list_box_outline, "Lists"),
        AppDrawerActionListItemData(R.drawable.microphone_outline, "Spaces"),
        AppDrawerActionListItemData(R.drawable.cash_multiple, "Monetization")
    )
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 31.dp)
    ) {
        listItems.forEach {
            AppDrawerActionListItem(iconId = it.iconId, actionName = it.actionName)
        }

        Divider(
            color = MaterialTheme.colors.onSurface.copy(alpha = .1f),
            modifier = Modifier
                .padding(
                    start = 22.dp,
                    end = 20.dp,
                    top = 28.dp,
                    bottom = 20.dp
                )
        )

        ExpandableList(sectionDataList)
        Image(modifier = Modifier
            .padding(top = 15.dp, start = 26.dp)
            .size(22.dp),
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "")
    }
}

@Composable
fun SectionItem(text: String, Icon : Int) {
    Row(verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(top = 15.dp)) {
        Image(modifier = Modifier.padding(start = 45.dp).size(22.dp),
            painter = painterResource(id = Icon),
            contentDescription = "")
        Text(
            text = text,
            modifier = Modifier.padding(start = 25.dp),
            fontFamily = chirpFamily,
            fontSize = 16.sp
        )
    }

}

@Composable
fun SectionHeader(text: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {
    Row(modifier = Modifier
        .clickable { onHeaderClicked() }
        .padding(vertical = 12.dp, horizontal = 16.dp)) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1.0f)
                .padding(start = 10.dp),
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        if (isExpanded) {
            Icon(modifier = Modifier.rotate(90f),
                painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                contentDescription = "",
            tint = colorResource(id = R.color.twitterBlue))
        } else {
            Icon(modifier = Modifier.rotate(270f),
                painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                contentDescription = "")
        }
    }
}

@Composable
fun ExpandableList(sections: List<SectionData> = sectionDataList) {
    val isExpandedMap = rememberSavableSnapshotStateMap {
        List(sections.size) { index: Int -> index to false }
            .toMutableStateMap()
    }

    LazyColumn(
        Modifier.heightIn(100.dp, 200.dp),
        content = {
            sections.onEachIndexed { index, sectionData ->
                Section(
                    sectionData = sectionData,
                    isExpanded = isExpandedMap[index] ?: false,
                    onHeaderClick = {
                        isExpandedMap[index] = !(isExpandedMap[index] ?: false)
                    }
                )
            }
        }
    )
}

fun LazyListScope.Section(
    sectionData: SectionData,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit
) {

    item {
        SectionHeader(
            text = sectionData.headerText,
            isExpanded = isExpanded,
            onHeaderClicked = onHeaderClick
        )
    }
    if(isExpanded) {
        items(sectionData.items) {
            SectionItem(text = it, if(it == "Settings and privacy") R.drawable.cog_outline else R.drawable.question_mark)
        }
    }
}

fun <K, V> snapshotStateMapSaver() = Saver<SnapshotStateMap<K, V>, Any>(
    save = { state -> state.toList() },
    restore = { value ->
        @Suppress("UNCHECKED_CAST")
        (value as? List<Pair<K, V>>)?.toMutableStateMap() ?: mutableStateMapOf<K, V>()
    }
)
@Composable
fun <K, V> rememberSavableSnapshotStateMap(init: () -> SnapshotStateMap<K, V>): SnapshotStateMap<K, V> =
    rememberSaveable(saver = snapshotStateMapSaver(), init = init)


@Composable
fun AppDrawerActionListItem(
    iconId: Int,
    actionName: String
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {})
            .padding(top = 11.dp, bottom = 11.dp, start = Dp(22f))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconId),
            contentDescription = "list item icon",
            modifier = Modifier.size(25.dp)
        )
        Text(
            modifier = Modifier.padding(start = 22.dp),
            text = actionName,
            fontFamily = chirpFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}


private data class AppDrawerActionListItemData(
    val iconId: Int,
    val actionName: String
)

data class SectionData(val headerText: String, val items: List<String>)
