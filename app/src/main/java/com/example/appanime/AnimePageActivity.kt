package com.example.appanime


import android.os.Bundle

import com.example.appanime.databinding.AnimePageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.squareup.picasso.Picasso

class AnimePageActivity : YouTubeBaseActivity() {
    private var YOUTUBE_API_KEY = "AIzaSyAQHZ6k1kY7TChvln_UCYvbPs1OMQRNi88"
    lateinit var youTubePlayerInit : YouTubePlayer.OnInitializedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = AnimePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val episodes = intent.getIntExtra("episodes",0)
            binding.txtTitle.text = intent.getStringExtra("title")
            Picasso.get().load(intent.getStringExtra("image")).into(binding.imgAnime)
            println("url = ${intent.getStringExtra("image")}")
            binding.txtSipnopsis.text = intent.getStringExtra("sipnopsis")
            binding.btnEpisodes.text = " ${episodes} Episodios"
            //video

            youTubePlayerInit = object : YouTubePlayer.OnInitializedListener{
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    p1?.loadVideo(intent.getStringExtra("trailer"))
                    println("id = ${intent.getStringExtra("trailer")}")
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    println("id = ${intent.getStringExtra("trailer")}")
                    Snackbar.make(binding.root, "Error al iniciar Youtube",Snackbar.LENGTH_SHORT).show()
                }
            } //end object
            binding.btnEpisodes.setOnClickListener {
                //val intent = YouTubeStandalonePlayer.createVideoIntent(this@AnimePageActivity, YOUTUBE_API_KEY, intent.getStringExtra("trailer"))
                //startActivity(intent)
                binding.vidTrailer.initialize(YOUTUBE_API_KEY, youTubePlayerInit)

            }
            val sizeCat = intent.getIntExtra("size-category",0)
            var i=0
            val cat = ArrayList<String>()
            while(i <= sizeCat){
                intent.getStringExtra("category$i")?.let { cat.add(it) }
                i++
            }
            val result = cat.asSequence().joinToString(separator = ", ")
            result.split(", ")
            binding.txtCategory.text = result
        }
    }


}