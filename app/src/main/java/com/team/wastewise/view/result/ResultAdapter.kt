package com.team.wastewise.view.result

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.wastewise.data.remote.response.Recommendation
import com.team.wastewise.databinding.RecyclerViewItemResultBinding

class ResultAdapter(private val listener: OnItemClickListener) : ListAdapter<Recommendation, ResultAdapter.ViewHolder>(DiffCallback()){

    // Creates and returns a ViewHolder for a single RecyclerView item.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Inflate the item layout using data binding.
        val binding = RecyclerViewItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    // Binds data from a Recommendation object to the ViewHolder.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // Retrieve the current item at the given position.
        holder.bind(item) // Bind the data to the ViewHolder.
    }

    class ViewHolder(
        private val binding: RecyclerViewItemResultBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        // Populates the views with data from the given Recommendation object.
        fun bind(result: Recommendation) {
            // Handle the click
            itemView.setOnClickListener {
                listener.onItemClick(result)
            }
            // Load the image into the ImageView using Glide.
            Glide.with(binding.root.context)
                .load(result.imageUrl)
                .into(binding.imageItemResult)
            // Set the title and description text.
            binding.tvItemName.text = result.title
            binding.tvItemDescription.text = result.description
        }
    }

    // Callback class to efficiently update the RecyclerView by comparing old and new items.
    class DiffCallback : DiffUtil.ItemCallback<Recommendation>() {
        // Checks if two items represent the same recommendation.
        override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
            return oldItem.title == newItem.title  // Assumes titles are unique identifiers.
        }

        // Checks if the contents of two items are the same.
        override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
            return oldItem == newItem // Uses equals() to compare all fields.
        }
    }

    interface OnItemClickListener {
        fun onItemClick(result: Recommendation)
    }
}