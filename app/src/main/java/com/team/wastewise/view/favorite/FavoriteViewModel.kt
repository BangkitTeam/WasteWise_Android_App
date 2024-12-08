package com.team.wastewise.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.response.FavoriteData
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.data.remote.response.FavoriteResponse
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository // Repository to handle image upload operations.
) : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is favorite Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _getAllFavorite = MutableLiveData<Result<List<FavoriteItem>>>()
    val getAllFavorite: LiveData<Result<List<FavoriteItem>>> = _getAllFavorite

    // LiveData to indicate whether the upload operation is in progress.
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllFavorite() {
        viewModelScope.launch {
            try {
                _loading.value = true // // Set loading state to true before starting the request.
                val response = favoriteRepository.getAllFavorite() // Get data Favorite via repository.
                _getAllFavorite.postValue(response) // Post the response to LiveData.
            } catch (e: Exception) {
                // Post failure result in case of an exception.
                _getAllFavorite.postValue(Result.failure(e))
            } finally {
                _loading.value = false
            }
        }
    }
}