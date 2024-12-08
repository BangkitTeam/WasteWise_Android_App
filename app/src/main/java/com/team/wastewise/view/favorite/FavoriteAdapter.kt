package com.team.wastewise.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.databinding.ItemFavoriteBinding

class FavoriteAdapter : ListAdapter<FavoriteItem, FavoriteAdapter.ViewHolder>(DiffCallback()) {

    // Creates and returns a ViewHolder for a single RecyclerView item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the item layout using data binding.
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Binds data from a Recommendation object to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // Retrieve the current item at the given position.
        holder.bind(item) // Bind the data to the ViewHolder.
    }

    class ViewHolder(
        private val binding: ItemFavoriteBinding
    ): RecyclerView.ViewHolder(binding.root) {
        // Populates the views with data from the given Recommendation object.
        fun bind(favorite: FavoriteItem) {
            // Load the image into the ImageView using Glide.
            Glide.with(binding.root.context)
                .load(favorite.imageUrl)
                .into(binding.imageItemFavorite)
            // Set the title and description text.
            binding.tvItemName.text = favorite.title
            binding.tvItemDescription.text = favorite.description
        }
    }

    // Callback class to efficiently update the RecyclerView by comparing old and new items.
    class DiffCallback : DiffUtil.ItemCallback<FavoriteItem>() {
        // Checks if two items represent the same recommendation.
        override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem.title == newItem.title  // Assumes titles are unique identifiers.
        }

        // Checks if the contents of two items are the same.
        override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem == newItem // Uses equals() to compare all fields.
        }
    }
}