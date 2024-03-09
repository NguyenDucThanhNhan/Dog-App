package com.example.a6_dogapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.a6_dogapp.R
import com.example.a6_dogapp.model.DogBreed
import com.squareup.picasso.Picasso
import java.util.Locale

class DogAdapter(private val dogBreeds: MutableList<DogBreed>) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>(), Filterable {
//    private var filteredDogBreeds: MutableList<DogBreed> = mutableListOf()
    private var filteredDogBreeds: MutableList<DogBreed> = dogBreeds

    init {
        filteredDogBreeds.addAll(dogBreeds)
    }

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvOrigin: TextView = itemView.findViewById(R.id.tv_origin)
        val ivAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        val imgHeart: ImageView = itemView.findViewById(R.id.imgHeart)

        init {
            itemView.setOnClickListener {
//                val dog = dogBreeds[adapterPosition]
                val dog = filteredDogBreeds[adapterPosition]
                val bundle = Bundle()
                bundle.putSerializable("dogBreed", dog)
                Navigation.findNavController(itemView).navigate(R.id.detailsFragment, bundle)
            }
        }

        init {
            imgHeart.setOnClickListener {
                imgHeart.setImageResource(R.drawable.clicked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_item, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
//        val dog = dogBreeds[position]
        val dog = filteredDogBreeds[position]
        holder.tvName.text = dog.name
        holder.tvOrigin.text = dog.origin
        Picasso.get().load(dog.url).into(holder.ivAvatar)
    }

    override fun getItemCount(): Int {
        return filteredDogBreeds.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString().toLowerCase(Locale.getDefault())
                val filterResults = FilterResults()
                if (queryString.isEmpty()) {
                    filterResults.values = dogBreeds
                } else {
                    val filteredList = mutableListOf<DogBreed>()
                    for (dog in dogBreeds) {
                        if (dog.name.toLowerCase(Locale.getDefault()).contains(queryString)) {
                            filteredList.add(dog)
                        }
                    }
                    filterResults.values = filteredList
                }
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredDogBreeds.clear()
                filteredDogBreeds.addAll(results?.values as MutableList<DogBreed>)
                notifyDataSetChanged()
            }
        }
    }
}

