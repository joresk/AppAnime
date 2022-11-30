package com.example.appanime.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appanime.R
import com.example.appanime.top.Data
import com.squareup.picasso.Picasso

class CarouselAdapter (private val carouselDataList: List<Data>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val anime =  carouselDataList[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.carousel_text)
        val image = holder.itemView.findViewById<ImageView>(R.id.imgCarousel)
        Picasso.get().load(anime.images.jpg.imageUrl).into(image)
        textView.text = anime.title
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}