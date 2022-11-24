package com.example.appanime.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appanime.R
import com.example.appanime.top.Data
import com.squareup.picasso.Picasso

class AdapterAnime(private val parentActivity: AppCompatActivity,
                   private val animes: List<Data>
): RecyclerView.Adapter<AdapterAnime.CustomViewHolder>() {
      inner class CustomViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anime_item,parent,false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val anime = animes[position]
        val view = holder.itemView
        val name = view.findViewById<TextView>(R.id.nameAnime)
        val image = view.findViewById<ImageView>(R.id.imageAnime)

        name.text = anime.title
        Picasso.get().load(anime.images.jpg.imageUrl).into(image)
    }

    override fun getItemCount(): Int {
        return animes.size
    }

}