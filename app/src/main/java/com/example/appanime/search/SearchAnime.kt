package com.example.appanime.search


import com.google.gson.annotations.SerializedName

data class SearchAnime(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("pagination")
    val pagination: Pagination
)