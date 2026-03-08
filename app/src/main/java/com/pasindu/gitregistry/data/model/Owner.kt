package com.pasindu.gitregistry.data.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName(value = "login")
    val login : String,
    @SerializedName(value = "avatar_url")
    val avatarUrl : String
)
