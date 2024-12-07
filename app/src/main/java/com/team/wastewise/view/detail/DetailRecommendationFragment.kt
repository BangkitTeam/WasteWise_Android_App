package com.team.wastewise.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.wastewise.R
import com.team.wastewise.data.remote.response.Data
import com.team.wastewise.data.remote.response.Recommendation
import com.team.wastewise.databinding.FragmentDetailRecommendationBinding
import com.team.wastewise.view.ViewModelFactory


class DetailRecommendationFragment : Fragment() {
    // Binding for the fragment's layout. Nullable to handle lifecycle management properly.
    private var _binding: FragmentDetailRecommendationBinding? = null
    private val binding get() = _binding!!

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

//        val mainActivity = requireActivity() as AppCompatActivity
//        mainActivity.supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_back)
//        }
        binding.toolbar.apply {
            title = "Detail Recommendation"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        val data = arguments?.getParcelable<Recommendation>("recommendation_data")
        data?.let { resultData ->
            viewModel.setData(resultData) // Update ViewModel with the new data
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}