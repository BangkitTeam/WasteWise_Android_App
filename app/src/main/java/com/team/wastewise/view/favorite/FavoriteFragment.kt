package com.team.wastewise.view.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.wastewise.R
import com.team.wastewise.databinding.FragmentFavoriteBinding
import com.team.wastewise.view.ViewModelFactory

class FavoriteFragment : Fragment() {
    // Binding for the fragment's layout. Nullable to handle lifecycle management properly.
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing UI-related data in a lifecycle-conscious way.
    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    // Adapter for displaying a list of recommendations in the RecyclerView.
    private lateinit var adapter : FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView with a vertical layout manager and the adapter.
        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorite.adapter = adapter

        observeViewModel()
        viewModel.getAllFavorite()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        // Observe loading state to show/hide progress indicator.
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Observe changes in the main data and update the UI with the results.
        viewModel.getAllFavorite.observe(viewLifecycleOwner) { result ->
            result.onSuccess { favorites ->
                if (favorites.isNotEmpty()) {
                    adapter.submitList(favorites)
                } else {
                    binding.textFavorite.text = getText(R.string.belum_ada_data)
                }
            }
            result.onFailure { error ->
                Toast.makeText(requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}