package com.team.wastewise.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.wastewise.data.remote.response.Recommendation

class DetailRecommendationViewModel : ViewModel() {

    private var _recommendation = MutableLiveData<Recommendation?>()
    val recommendation: LiveData<Recommendation?> = _recommendation

    fun setData(data: Recommendation) {
        _recommendation.value = data
    }
}