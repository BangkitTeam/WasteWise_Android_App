package com.team.wastewise.view.result

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.wastewise.MainActivity
import com.team.wastewise.R
import com.team.wastewise.data.remote.response.Data
import com.team.wastewise.data.remote.response.Recommendation
import com.team.wastewise.databinding.FragmentResultBinding
import com.team.wastewise.view.ViewModelFactory

class ResultFragment : Fragment(), ResultAdapter.OnItemClickListener {
    // Binding for the fragment's layout. Nullable to handle lifecycle management properly.
    private var _binding : FragmentResultBinding? = null
    private val binding get() = _binding!!

    // ViewModel for managing UI-related data in a lifecycle-conscious way.
    private val viewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    // Adapter for displaying a list of recommendations in the RecyclerView.
    private val adapter = ResultAdapter(this)

    private var data: Data? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the BottomNavigationView when viewing the result fragment.
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE

        binding.toolbar.apply {
            title = "Result"
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                navigateToMainActivity()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToMainActivity()
        }

        // Set up the RecyclerView with a vertical layout manager and the adapter.
        binding.resultRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.resultRecyclerView.adapter = adapter

        // Retrieve data passed through navigation arguments.
        data = arguments?.getParcelable("data")
        data?.let { resultData ->
            viewModel.setData(resultData) // Update ViewModel with the new data
        }

        // Observe changes in ViewModel's data and update the UI accordingly.
        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using data binding.
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {

        // Observe loading state to show/hide progress indicator.
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe changes in the main data and update the UI with the results.
        viewModel.data.observe(viewLifecycleOwner) { resultData ->
            if (resultData?.confidence!! < 75) {
                binding.warningConfidenceScore.visibility = View.VISIBLE
                binding.warningConfidenceScore.text = """
                Threshold Score is
                below the Standard,
                Try Again with 
                another Image.
                """.trimIndent()
                Glide.with(this)  // Use Glide to load and display the image.
                    .load(resultData.image_url)
                    .into(binding.imageResult)
                binding.resultValueConfidence.visibility = View.GONE
                binding.resultValuePrediction.visibility = View.GONE
                binding.resultRecyclerView.visibility = View.GONE
                binding.recycleRecommendationWarning.visibility = View.VISIBLE
            } else {
                Glide.with(this)  // Use Glide to load and display the image.
                    .load(resultData.image_url)
                    .into(binding.imageResult)
                // Update confidence and prediction text views
                binding.resultValueConfidence.text = "${resultData.confidence}%"
                binding.resultValuePrediction.text = resultData.prediction
                binding.recycleRecommendationWarning.visibility = View.GONE
            }
        }

        // Observe recommendations and pass them to the adapter to update the RecyclerView.
        viewModel.recommendation.observe(viewLifecycleOwner) { recommendation ->
            if (recommendation.isNotEmpty()) {
                adapter.submitList(recommendation)  // Update adapter's data.
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish() // Menutup aktivitas fragment saat ini
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetData() // Clear data from ViewModel when the view is destroyed.
        _binding = null // Avoid memory leaks by nullifying the binding reference.
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Nullify binding reference for additional safety.
    }

    override fun onItemClick(result: Recommendation) {
        val bundle = Bundle().apply {
            putParcelable("recommendation_data", result)
            putInt("userId", data?.user_id ?: -1)
        }
        findNavController().navigate(R.id.action_navigation_result_to_navigation_detail_recommendation, bundle)
    }
}