package com.team.wastewise.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.response.FavoriteItem
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository // Repository to handle image upload operations.
) : ViewModel() {

    // MediatorLiveData to observe and transform data from other LiveData objects,
    // specifically extracting favorite from the main data.
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