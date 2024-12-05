package com.team.wastewise.view.upload

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    // Binding object for accessing UI elements in the fragment layout.
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    // Stores the current image URI being processed in the fragment.
    private var currentImageUri: Uri? = null

    // ViewModel to handle business logic and UI-related data.
    private val viewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the BottomNavigationView when the user is in this fragment.
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.visibility = View.GONE

        // Set click listeners for the gallery, camera, and analyze buttons.
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnAnalyze.setOnClickListener { uploadImage() }

        // Observe the current image URI from the ViewModel and display the image if it exists.
        viewModel.currentImageUri.observe(requireActivity()) { uri ->
            if (uri != null) {
                showImage(uri)
            }
        }
    }

    // Inflates the fragment's layout and initializes the binding object.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    // ActivityResultLauncher for selecting an image from the gallery.
    private val launchGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.setCurrentImageUri(uri) // Save the selected image URI in the ViewModel.
        }
    }

    // Starts the gallery intent to pick an image.
    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    // Displays the selected image in the ImageView.
    private fun showImage(uri: Uri) {
        binding.imageUpload.setImageURI(uri)
    }

    // Starts the camera intent to take a picture.
    private fun startCamera() {
        // Generate a URI for the captured image and save it in the ViewModel.
        val uri = getImageUri(requireContext())
        currentImageUri = getImageUri(requireContext())
        viewModel.setCurrentImageUri(uri)
        launcherIntentCamera.launch(uri)
    }

    // ActivityResultLauncher for taking a picture using the camera.
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            // Save the captured image URI in the ViewModel if successful.
            currentImageUri?.let {
                viewModel.setCurrentImageUri(it)
            }
        } else {
            viewModel.setCurrentImageUri(null) // Reset URI if the capture failed.
        }
    }

    // Handles image upload and navigates to the ResultFragment upon success.
    private fun uploadImage() {
        val uri = viewModel.currentImageUri.value
        if (uri != null) {
            // Convert the image URI to a file for uploading.
            val imageFile = uriToFile(uri, requireContext())
            viewModel.uploadImage(imageFile)

            // Show or hide the loading indicator based on the upload state.
            viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            // Handle the result of the image upload.
            viewModel.uploadImage.observe(viewLifecycleOwner) { result ->
                result.onSuccess { response ->
                    // Navigate to the ResultFragment with the uploaded data.
                    val bundle = Bundle().apply {
                        putParcelable("data", response.data)
                    }
                    findNavController().navigate(R.id.action_navigation_upload_to_resultFragment, bundle)
                }.onFailure { throwable ->
                    // Show an error message if the upload failed.
                    Toast.makeText(requireContext(), "Upload failed: ${throwable.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Show a message if no image was selected for upload.
            Toast.makeText(requireContext(), "No image selected!", Toast.LENGTH_SHORT).show()
        }
    }

    // Called when the fragment's view is destroyed, ensuring cleanup and visibility adjustments.
    override fun onDestroyView() {
        super.onDestroyView()
        val navController = findNavController()
        val destinationId = navController.currentDestination?.id
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)

        // Show BottomNavigationView only if navigating to the HomeFragment.
        if (destinationId == R.id.navigation_home) {
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            bottomNavigationView.visibility = View.GONE
        }
        _binding = null
    }

    // Called when the fragment is completely destroyed, clearing the binding reference.
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}