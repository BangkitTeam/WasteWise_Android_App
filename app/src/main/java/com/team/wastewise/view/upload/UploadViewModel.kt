package com.team.wastewise.view.upload

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.UploadRepository
import com.team.wastewise.data.remote.response.FileUploadResponse
import kotlinx.coroutines.launch
import java.io.File

class UploadViewModel(
    private val uploadRepository: UploadRepository // Repository to handle image upload operations.
) : ViewModel() {

    // LiveData to store the currently selected image URI.
    private val _currentImageUri = MutableLiveData<Uri?>()
    var currentImageUri: LiveData<Uri?> = _currentImageUri

    // LiveData to store the result of the image upload operation.
    private val _uploadImage = MutableLiveData<Result<FileUploadResponse>>()
    val uploadImage: LiveData<Result<FileUploadResponse>> = _uploadImage

    // LiveData to indicate whether the upload operation is in progress.
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Sets the current image URI selected by the user.
    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    // Uploads the provided image file using the repository and updates the upload state.
    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            try {
                _loading.value = true // Set loading state to true before starting the upload.
                val response = uploadRepository.uploadImage(imageFile) // Upload image via repository.
                _uploadImage.postValue(response) // Post the upload response to LiveData.
            } catch (e: Exception) {
                // Post failure result in case of an exception.
                _uploadImage.postValue(Result.failure(e))
            } finally {
                // Reset the loading state once the operation is complete.
                _loading.value = false
            }
        }
    }
}