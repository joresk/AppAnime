package com.example.appanime.top


import com.google.gson.annotations.SerializedName

data class TopAnime(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
)