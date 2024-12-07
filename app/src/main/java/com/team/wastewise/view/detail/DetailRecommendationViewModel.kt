package com.team.wastewise.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.wastewise.data.remote.response.Recommendation

class DetailRecommendationViewModel : ViewModel() {

    // LiveData to store the primary data object used in the detail.
    private var _recommendation = MutableLiveData<Recommendation?>()
    val recommendation: LiveData<Recommendation?> = _recommendation

    // Sets the primary detail data.
    fun setData(data: Recommendation) {
        _recommendation.value = data
    }
}