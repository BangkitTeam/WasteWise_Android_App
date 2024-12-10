package com.team.wastewise.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.response.AddFavoriteRequest
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.data.remote.response.Recommendation
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailRecommendationViewModel(
    private val favoriteRepository: FavoriteRepository // Repository to handle image upload operations.
) : ViewModel() {

    // LiveData to store the primary data object used in the detail.
    private var _recommendation = MutableLiveData<Recommendation?>()
    val recommendation: LiveData<Recommendation?> = _recommendation

    private val _addFavoriteResult = MutableLiveData<Result<Unit>>()
    val addFavoriteResult: LiveData<Result<Unit>> = _addFavoriteResult

    // Sets the primary detail data.
    fun setData(data: Recommendation) {
        _recommendation.value = data
    }

    fun addFavorite(userRecommendationId: Int) {
        viewModelScope.launch {
            try {
//                val request = AddFavoriteRequest(userRecommendationId)
                val response = favoriteRepository.addFavorite(userRecommendationId)
                response.onSuccess {
                    Log.d("ViewModel", "Favorite successfully added")
                }.onFailure {
                    Log.e("ViewModel", "Error adding favorite: ${it.message}")
                }
                _addFavoriteResult.postValue(response)

            } catch (e: Exception) {
                Log.e("ViewModel", "Unexpected exception: ${e.message}")
            }
        }
    }
}