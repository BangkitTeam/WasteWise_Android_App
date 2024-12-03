package com.team.wastewise.view.upload

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.wastewise.R
import com.team.wastewise.databinding.FragmentUploadBinding
import com.team.wastewise.view.ViewModelFactory
import com.team.wastewise.view.utils.getImageUri
import com.team.wastewise.view.utils.uriToFile

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnAnalyze.setOnClickListener { uploadImage() }

        viewModel.currentImageUri.observe(requireActivity()) { uri ->
            if (uri != null) {
                showImage(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val launchGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setCurrentImageUri(uri)
        }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage(uri: Uri) {
        binding.imageUpload.setImageURI(uri)
    }

    private fun startCamera() {
        val uri = getImageUri(requireContext())
        currentImageUri = getImageUri(requireContext())
        viewModel.setCurrentImageUri(uri)
        launcherIntentCamera.launch(uri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            currentImageUri?.let {
                viewModel.setCurrentImageUri(it)
            }
        } else {
            viewModel.setCurrentImageUri(null)
        }
    }

    private fun uploadImage() {
        val uri = viewModel.currentImageUri.value
        if (uri != null) {
            val imageFile = uriToFile(uri, requireContext())
            viewModel.uploadImage(imageFile)
            viewModel.uploadImage.observe(viewLifecycleOwner) { result ->
                result.onSuccess {
                    val action = UploadFragmentDirections.actionNavigationUploadToResultFragment()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.VISIBLE
        _binding = null
    }
}