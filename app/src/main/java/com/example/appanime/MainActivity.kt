package com.example.appanime

import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.appanime.adapter.AdapterAnime
import com.example.appanime.adapter.CarouselAdapter
import com.example.appanime.databinding.ActivityMainBinding
import com.example.appanime.service.AnimeService
import com.example.appanime.top.Data
import com.example.appanime.top.TopAnime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity() {
    private lateinit var resp : List<Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val carousel = binding.carousel

        carousel.apply {
            //service
            val animeService = AnimeService.create()
            val call = animeService.getTopAnimes("airing",24)

            call.enqueue(object : Callback<TopAnime>{
                override fun onFailure(call: Call<TopAnime>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"Fallo al obtener datos", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<TopAnime>, response: Response<TopAnime>) {
                    if (response.body() != null) {
                        resp = response.body()!!.data
                        val adapt = CarouselAdapter(resp)
                        carousel.adapter = adapt
                        carousel.layoutManager = LinearLayoutManager(this@MainActivity,
                        LinearLayoutManager.HORIZONTAL, false)
                    }
                }
            })
        }

        binding.apply {
            val animeService = AnimeService.create()
            val call = animeService.getTopAnimes("bypopularity",24)
            call.enqueue(object : Callback<TopAnime>{
                override fun onFailure(call: Call<TopAnime>, t: Throwable) {

                }

                override fun onResponse(call: Call<TopAnime>, response: Response<TopAnime>) {
                    if (response.body() != null) {
                        val top = response.body()!!.data
                        val adapt = AdapterAnime(this@MainActivity,top)
                        animeRecyclerView.adapter = adapt
                        animeRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
                        adapt.onItemClick={
                            val intent = Intent(this@MainActivity, AnimePageActivity::class.java)
                            sendData(it,intent)

                        }
                    }
                }
            })
            //button
            binding.searchInputLayout.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchInputLayout.clearFocus()
                    val searchedAnime = binding.searchInputLayout.query.toString()
                    println("buscar: $searchedAnime")
                    val callSearchedAnime = animeService.getSearchedAnime(searchedAnime,24)
                    callSearchedAnime.enqueue(object : Callback<TopAnime> {
                        override fun onResponse(
                            call: Call<TopAnime>,
                            response: Response<TopAnime>
                        ) {
                            if (response.body() != null) {
                                val searchedAnimes = response.body()!!.data
                                val adapt = AdapterAnime(this@MainActivity,searchedAnimes)
                                animeRecyclerView.adapter = adapt
                                animeRecyclerView.layoutManager = GridLayoutManager(this@MainActivity,3)
                                adapt.onItemClick={
                                    val intent = Intent(this@MainActivity, AnimePageActivity::class.java)
                                    sendData(it,intent)

                                }
                            }
                        }

                        override fun onFailure(call: Call<TopAnime>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    }) //end callsearched
                 return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Toast.makeText(this@MainActivity, "Buscando anime..", Toast.LENGTH_SHORT).show()
                    return false
                }

            }) //end search

            //end Button
        }

    }//end onCreate

    private fun sendData(it: Data, intent: Intent) {
        intent.putExtra("title" , it.title)
        intent.putExtra("episodes",it.episodes)
        intent.putExtra("image", it.images.jpg.imageUrl)
        intent.putExtra("sipnopsis", it.synopsis)
        intent.putExtra("size-category",it.genres.size)
        intent.putExtra("trailer",it.trailer.youtubeId)

        var aux=1
        it.genres.forEach {
            intent.putExtra("category$aux", it.name)
            aux++
        }
        startActivity(intent)
    }

}