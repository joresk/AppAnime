package com.example.appanime.service

import com.example.appanime.search.SearchAnime
import com.example.appanime.top.TopAnime
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    fun getTopAnimes(): Call<TopAnime>
    @GET("search/anime")
    fun getSearchedAnime(@Query("q")queryString: String): Call<TopAnime>
    companion object {
        val URL = "https://api.jikan.moe/v4/"
        fun create(): AnimeService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(AnimeService::class.java)
        }
    }
}