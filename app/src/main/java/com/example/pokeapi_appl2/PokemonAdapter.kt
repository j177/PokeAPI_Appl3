package com.example.pokeapi_appl2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter(private val pokemonList: List<MainActivity.Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val idTextView: TextView = itemView.findViewById(R.id.itemIdTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.itemTypeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.idTextView.text = "ID: ${currentItem.id}"
        holder.nameTextView.text = "Name: ${currentItem.name}"
        holder.typeTextView.text = "Type: ${currentItem.type}"

        // Load image using DownloadImageTask or any other preferred method
        // For simplicity, I'm assuming you have a URL in the Pokemon object
        MainActivity.DownloadImageTask(holder.imageView).execute(currentItem.imageUrl)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}