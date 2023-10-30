package com.JetpackPractice.twitterui.viewModel

import androidx.lifecycle.ViewModel
import com.JetpackPractice.twitterui.R
import com.JetpackPractice.twitterui.domain.model.AccountModel
import com.JetpackPractice.twitterui.domain.model.PostModel

class MainViewModel : ViewModel() {
    private val homeScreenPosts: List<PostModel> = listOf(
        PostModel(
            "Sergeevme",
            "@sergeevme",
            "15h",
            R.drawable.home_post_profile_pic_01,
            "That's a good article to read \uD83D\uDCD6\n" +
                    "\n" +
                    "Shimmer & Shadow Loading Effect Animation with Jetpack Compose\n",
            R.drawable.home_post_pic_1,
            2,
            1,
            4,
            50,
            false,
            true,
            true
        ), PostModel(
            "Google Play business community",
            "@GooglePlayBiz",
            "16h",
            R.drawable.home_post_profile_pic_02,
            "We’ve released a major update to our Play Store Listing Certificate course to boost user acquisition and store conversions!→ https://goo.gle/3EuSS8s\n" +
                    "\n" +
                    "\uD83D\uDCDATake the training and watch the new video series\n" +
                    "\uD83D\uDCDDPass the exam, get certified\n" +
                    "\uD83E\uDDEAUse custom store listings and experiments",
            R.drawable.home_post_pic_2,
            45,
            1,
            10,
            29003,
            true,
            true,
            true
        ), PostModel(
            "Android Developers",
            "@AndroidDev",
            "Oct 5",
            R.drawable.home_post_profile_pic_04,
            "Wear OS 4 brings new user experiences to more devices. Discover key behavior changes and learn how to interact with new features:\n" +
                    "\n" +
                    "⌚ Background body sensor permission\n" +
                    "⌚ More seamless data transfer\n" +
                    "⌚ New Wear OS 4 emulator",
            R.drawable.home_post_pic_04,
            89,
            10,
            16,
            21000,
            true,
            true,
            true
        ), PostModel(
            "Sergeevme",
            "@sergeevme",
            "45mn",
            R.drawable.home_post_profile_pic_01,
            "Don’t Reinvent The Wheel (2023)\uD83D\uDCF1\n" +
                    "\uD83D\uDC49 Use Glide or Koin for image loading\n" +
                    "\uD83D\uDC49 Use Retrofit for networking\n" +
                    "\uD83D\uDC49 Use Gson or Kotlin Serialization for JSON parsing\n" +
                    "\uD83D\uDC49 Use View Binding and Data Binding",
            -1,
            2,
            0,
            0,
            20,
            false,
            true,
            false
        ), PostModel(
            "Daniel Atitienei",
            "@danielatitienei",
            "Oct 9",
            R.drawable.home_post_profile_pic_03,
            "Hey #AndroidDev, did you know how easy is to integrate the floating action button in the material3 bottom bar?\n",
            R.drawable.home_post_pic_03,
            15,
            2,
            10,
            29003,
            true,
            true,
            true
        ), PostModel(
            "Kotlin by JetBrains",
            "@kotlin",
            "Oct 4",
            R.drawable.home_post_profile_pic_05,
            "Day 2 at \n" +
                    "@devoxx\n" +
                    " Belgium!\n" +
                    "\n" +
                    "Stop by our booth to chat about your experience, see Kotlin Notebook in action, learn about Kotlin Multiplatform, and more.",
            R.drawable.home_post_pic_05,
            15,
            2,
            3,
            9370,
            true,
            true,
            true
        )
    )
    private val accountsData: List<AccountModel> = listOf(
        AccountModel.DEFAULT_ACCOUNT,
        AccountModel(
            "Google Play business community",
            "@GooglePlayBiz",
            "Best practices, insights and news to grow your app or game. This is an official Google Play channel.",
            R.drawable.home_post_profile_pic_02,
            R.drawable.cover_pic_01,
            R.drawable.cover_pic_blurred_01,
            "April 2017",
            "The Google Play Store",
            "315",
            "106.8k",
            listOf(
                PostModel(
                    "Google Play business community",
                    "@GooglePlayBiz",
                    "16h",
                    R.drawable.home_post_profile_pic_02,
                    "We’ve released a major update to our Play Store Listing Certificate course to boost user acquisition and store conversions!→ https://goo.gle/3EuSS8s\n" +
                            "\n" +
                            "\uD83D\uDCDATake the training and watch the new video series\n" +
                            "\uD83D\uDCDDPass the exam, get certified\n" +
                            "\uD83E\uDDEAUse custom store listings and experiments",
                    R.drawable.home_post_pic_2,
                    45,
                    1,
                    10,
                    29003,
                    true,
                    true,
                    true
                ),
                PostModel(
                    "Google Play business community",
                    "@GooglePlayBiz",
                    "16h",
                    R.drawable.home_post_profile_pic_02,
                    "POV: you’re a #developer who needs help monetizing your app and just heard about the new and improved Play Commerce website → https://goo.gle/playcommerce",
                    R.drawable.inner_post_01,
                    6,
                    3,
                    2,
                    1603,
                    true,
                    true,
                    true
                ),
                PostModel(
                    "Google Play business community",
                    "@GooglePlayBiz",
                    "16h",
                    R.drawable.home_post_profile_pic_02,
                    "POV: you’re a #developer who needs help monetizing your app and just heard about the new and improved Play Commerce website → https://goo.gle/playcommerce",
                    R.drawable.inner_post_02,
                    6,
                    3,
                    2,
                    1603,
                    true,
                    true,
                    true
                )
            )
        ),
        AccountModel(
            "Android Developers",
            "@AndroidDev",
            "Get the latest Android news, best practices, live videos, demonstrations, tutorials, and more!",
            R.drawable.home_post_profile_pic_04,
            R.drawable.dummy_cover_pic,
            R.drawable.dummy_cover_pic_blurred,
            "November 2009",
            "Mountain View, CA",
            "386",
            "2.2M",
            listOf(
                PostModel(
                    "Android Developers",
                    "@AndroidDev",
                    "Oct 5",
                    R.drawable.home_post_profile_pic_04,
                    "Wear OS 4 brings new user experiences to more devices. Discover key behavior changes and learn how to interact with new features:\n" +
                            "\n" +
                            "⌚ Background body sensor permission\n" +
                            "⌚ More seamless data transfer\n" +
                            "⌚ New Wear OS 4 emulator",
                    R.drawable.home_post_pic_04,
                    89,
                    10,
                    16,
                    21000,
                    true,
                    true,
                    true
                ),
                PostModel(
                    "Android Developers",
                    "@AndroidDev",
                    "Oct 5",
                    R.drawable.home_post_profile_pic_04,
                    "Wear OS 4 brings new user experiences to more devices. Discover key behavior changes and learn how to interact with new features:\n" +
                            "\n" +
                            "⌚ Background body sensor permission\n" +
                            "⌚ More seamless data transfer\n" +
                            "⌚ New Wear OS 4 emulator",
                    R.drawable.inner_post_03,
                    134,
                    27,
                    16,
                    31000,
                    true,
                    true,
                    true
                )
            )
        ),
        AccountModel.DEFAULT_ACCOUNT,
        AccountModel.DEFAULT_ACCOUNT
    )
    private val searchScreenPosts: List<MainTopic> = listOf(
        MainTopic("Technology",
        listOf(
            PostModel(
                "Kotlin by JetBrains",
                "@kotlin",
                "Oct 4",
                R.drawable.home_post_profile_pic_05,
                "Day 2 at \n" +
                        "@devoxx\n" +
                        " Belgium!\n" +
                        "\n" +
                        "Stop by our booth to chat about your experience, see Kotlin Notebook in action, learn about Kotlin Multiplatform, and more.",
                R.drawable.home_post_pic_05,
                15,
                2,
                3,
                9370,
                true,
                true,
                true
            ),
            PostModel(
                "Daniel Atitienei",
                "@danielatitienei",
                "Oct 9",
                R.drawable.home_post_profile_pic_03,
                "Hey #AndroidDev, did you know how easy is to integrate the floating action button in the material3 bottom bar?\n",
                R.drawable.home_post_pic_03,
                15,
                2,
                10,
                29003,
                true,
                true,
                true
            ),
            PostModel(
                "Sergeevme",
                "@sergeevme",
                "45mn",
                R.drawable.home_post_profile_pic_01,
                "Don’t Reinvent The Wheel (2023)\uD83D\uDCF1\n" +
                        "\uD83D\uDC49 Use Glide or Koin for image loading\n" +
                        "\uD83D\uDC49 Use Retrofit for networking\n" +
                        "\uD83D\uDC49 Use Gson or Kotlin Serialization for JSON parsing\n" +
                        "\uD83D\uDC49 Use View Binding and Data Binding",
                -1,
                2,
                0,
                0,
                20,
                false,
                true,
                false
            )
        )
        )
    )
    private val myAccountData: AccountModel =   AccountModel(
        "mahfoudh aggoun",
        "@Mahfoudh_Aggn",
        "",
        R.drawable.default_profile_pic,
        R.drawable.my_profile_cover,
        R.drawable.my_profile_cover,
        "December 2014",
        "Algeria",
        "386",
        "5",
        null
    )



    fun getHomeScreenPosts(): List<PostModel> {
        return homeScreenPosts
    }

    fun getAccountData(index: Int): AccountModel {
        return accountsData[index]
    }

    fun getSearchScreenPosts() : List<MainTopic> {
        return searchScreenPosts
    }

    data class MainTopic(
        val topicName : String ,
        val posts : List<PostModel>
    )
}