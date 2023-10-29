package com.example.pokeapi_appl2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Typeface

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

        MainActivity.DownloadImageTask(holder.imageView).execute(currentItem.imageUrl)

        val typeface = Typeface.create(ResourcesCompat.getFont(holder.itemView.context, R.font.architects_daughter), Typeface.NORMAL)
        holder.idTextView.typeface = typeface
        holder.nameTextView.typeface = typeface
        holder.typeTextView.typeface = typeface
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}