package com.team.wastewise.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.response.AddFavoriteRequest
import com.team.wastewise.data.remote.response.FavoriteItem
import com.team.wastewise.data.remote.response.Recommendation
import kotlinx.coroutines.launch

class DetailRecommendationViewModel(
    private val favoriteRepository: FavoriteRepository // Repository to handle image upload operations.
) : ViewModel() {

    // LiveData to store the primary data object used in the detail.
    private var _recommendation = MutableLiveData<Recommendation?>()
    val recommendation: LiveData<Recommendation?> = _recommendation

    private val _addFavoriteResult = MutableLiveData<Result<FavoriteItem>>()
    val addFavoriteResult: LiveData<Result<FavoriteItem>> = _addFavoriteResult

    // Sets the primary detail data.
    fun setData(data: Recommendation) {
        _recommendation.value = data
    }

    fun addFavorite(userId: Int, userRecommendationId: Int) {
        viewModelScope.launch {
            try {
                val request = AddFavoriteRequest(userId, userRecommendationId)
                val response = favoriteRepository.addFavorite(request)
                _addFavoriteResult.postValue(response)
            } catch (e: Exception) {
                _addFavoriteResult.postValue(Result.failure(e))
            }
        }
    }

}