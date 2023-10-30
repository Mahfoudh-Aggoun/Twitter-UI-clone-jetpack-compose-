package com.JetpackPractice.twitterui.domain.model

import com.JetpackPractice.twitterui.R

data class PostModel (
    val username: String,
    val accountname: String,
    val date: String,
    val profilePic: Int,
    val text: String,
    val image: Int = -1,
    val likes: Int,
    val comments: Int,
    val retweets: Int,
    val views: Int,
    val verified: Boolean,
    val hasText: Boolean,
    val hasPic: Boolean,
) {

    companion object {
        val DEFAULT_POST = PostModel(
           "mahfoudh aggoun",
            "@Mahfoudh...",
            "2d",
            R.drawable.default_profile_pic,
            "Filler text is text that shares some characteristics of a real written text, but is random or otherwise generated. It may be used to display a sample of fonts, generate text for testing, or to spoof an e-mail spam filter.",
            R.drawable.icon,
            56,
            10,
            2,
            1005,
            true,
            true,
            false
        )
    }

}