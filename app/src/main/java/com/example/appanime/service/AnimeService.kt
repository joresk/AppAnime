package com.example.appanime.service

import com.example.appanime.top.TopAnime
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {

    @GET("top/anime")
    fun getTopAnimes(
        @Query("filter")queryString: String,
        @Query("limit")queryInt: Int

        ): Call<TopAnime>
    @GET("anime")
    fun getSearchedAnime(
        @Query("q")queryString: String,
        @Query("limit") page: Int,
        ): Call<TopAnime>
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