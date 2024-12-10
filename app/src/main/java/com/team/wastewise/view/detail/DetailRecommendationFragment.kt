package com.team.wastewise.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.wastewise.R
import com.team.wastewise.data.remote.response.Recommendation
import com.team.wastewise.databinding.FragmentDetailRecommendationBinding
import com.team.wastewise.view.ViewModelFactory


    class DetailRecommendationFragment : Fragment() {
        // Binding for the fragment's layout. Nullable to handle lifecycle management properly.
        private var _binding: FragmentDetailRecommendationBinding? = null
        private val binding get() = _binding!!

        // ViewModel for managing UI-related data in a lifecycle-conscious way.
        private val viewModel by viewModels<DetailRecommendationViewModel> {
            ViewModelFactory.getInstance(requireContext())
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout for this fragment
            _binding = FragmentDetailRecommendationBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Hide the BottomNavigationView when viewing the result fragment.
            val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavigationView.visibility = View.GONE

            binding.toolbar.apply {
                title = "Detail Recommendation"
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    requireActivity().onBackPressed()
                }
            }

            // Retrieve data passed through navigation arguments.
            val data = arguments?.getParcelable<Recommendation>("recommendation_data")
            data?.let { resultData ->
                viewModel.setData(resultData) // Update ViewModel with the new data
            }
            val userId = arguments?.getInt("userId")

            binding.favButton.setOnClickListener {
                val userRecommendationId = data?.id
                if (userId != null && userRecommendationId != null) {
                    viewModel.addFavorite(userId, userRecommendationId)
                }
//                binding.favButton.visibility = View.GONE
//                binding.favButtonFill.visibility = View.VISIBLE
            }

            observeViewModel()
        }

        private fun observeViewModel() {
            viewModel.recommendation.observe(viewLifecycleOwner) {resultData ->
                if (resultData != null) {
                    binding.tvDetailTitle.text = resultData.title
                    binding.tvDetailDesc.text = resultData.description
                    Glide.with(requireActivity())
                        .load(resultData.imageUrl)
                        .into(binding.ivDetailRecommend)
                }
            }
            viewModel.addFavoriteResult.observe(viewLifecycleOwner) { result ->
                result.onSuccess {
                    showFavoriteFillButton()
                }.onFailure { error ->
                    if (error.message == "Favorite already exists") {
                        showFavoriteOutlineButton()
                        Toast.makeText(requireContext(), "Favorite already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to add favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        private fun showFavoriteOutlineButton() {
            binding.favButton.visibility = View.VISIBLE
            binding.favButtonFill.visibility = View.GONE
        }

        private fun showFavoriteFillButton() {
            binding.favButton.visibility = View.GONE
            binding.favButtonFill.visibility = View.VISIBLE
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }