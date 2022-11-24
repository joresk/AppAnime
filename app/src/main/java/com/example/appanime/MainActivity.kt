package com.example.appanime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appanime.adapter.AdapterAnime
import com.example.appanime.databinding.ActivityMainBinding
import com.example.appanime.search.SearchAnime
import com.example.appanime.service.AnimeService
import com.example.appanime.top.TopAnime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val animeService = AnimeService.create()
            val call = animeService.getTopAnimes()
            call.enqueue(object : Callback<TopAnime>{
                override fun onFailure(call: Call<TopAnime>, t: Throwable) {

                }

                override fun onResponse(call: Call<TopAnime>, response: Response<TopAnime>) {
                    if (response.body() != null) {
                        val top = response.body()!!.data
                        animeRecyclerView.adapter = AdapterAnime(this@MainActivity,top)
                        animeRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
                    }
                }
            })
            //button
            binding.btnSearch.setOnClickListener {
                val searchedAnime = binding.searchInputEdit.text.toString()
                val callSearchedAnime = animeService.getSearchedAnime(searchedAnime)

                callSearchedAnime.enqueue(object : Callback<TopAnime> {

                    override fun onResponse(
                        call: Call<TopAnime>,
                        response: Response<TopAnime>
                    ) {
                        if (response.body() != null) {
                            val searchedAnimes = response.body()!!.data
                            animeRecyclerView.adapter = AdapterAnime(this@MainActivity,searchedAnimes)
                            animeRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
                        }
                    }

                    override fun onFailure(call: Call<TopAnime>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }//end Button
        }


    }//end onCreate
}