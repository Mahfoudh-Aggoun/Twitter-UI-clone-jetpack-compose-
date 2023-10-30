package com.JetpackPractice.twitterui.domain.model

import com.JetpackPractice.twitterui.R

data class AccountModel(
    val username: String,
    val accountname: String,
    val bio : String,
    val profilePic: Int,
    val coverPic: Int,
    val blurredCoverPic: Int,
    val dateJoined : String,
    val location : String,
    val following : String,
    val followers : String,
    val posts : List<PostModel>?
){
    companion object{
        val DEFAULT_ACCOUNT = AccountModel(
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
    }
}