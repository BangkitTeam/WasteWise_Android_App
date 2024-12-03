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
    private val uploadRepository: UploadRepository
) : ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri?>()
    var currentImageUri: LiveData<Uri?> = _currentImageUri

    private val _uploadImage = MutableLiveData<Result<FileUploadResponse>>()
    val uploadImage: LiveData<Result<FileUploadResponse>> = _uploadImage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = uploadRepository.uploadImage(imageFile)
                _uploadImage.postValue(response)
            } catch (e: Exception) {
                _uploadImage.postValue(Result.failure(e))
            } finally {
                _loading.value = false
            }
        }
    }
}