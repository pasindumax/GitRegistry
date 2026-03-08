package com.pasindu.gitregistry.data.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "full_name")
    val fullName: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName(value = "stargazers_count")
    val stargazersCount: Int,
    @SerializedName(value = "owner")
    val owner: Owner,
)
