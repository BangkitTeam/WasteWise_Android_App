package com.team.wastewise.view.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.wastewise.data.remote.response.Data
import com.team.wastewise.data.remote.response.Recommendation

class ResultViewModel : ViewModel() {

    // LiveData to store the primary data object used in the result.
    private val _data = MutableLiveData<Data?>()
    val data: LiveData<Data?> = _data

    // MediatorLiveData to observe and transform data from other LiveData objects,
    // specifically extracting recommendations from the main data.
    private val _recommendation = MediatorLiveData<List<Recommendation>>()
    val recommendation: LiveData<List<Recommendation>> = _recommendation

    // LiveData to indicate the loading state of the result data.
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Initialize the ViewModel by linking _data changes to _recommendation updates.
    init {
        _recommendation.addSource(_data) { data ->
            // When _data changes, extract recommendations and update _recommendation.
            _recommendation.value = data?.recommendations
        }
    }

    // Sets the primary result data and updates the loading state.
    fun setData(resultData: Data) {
        _loading.value = true
        _data.value = resultData
        _loading.value = false
    }

    // Resets the primary result data, effectively clearing it.
    fun resetData() {
        _data.value = null
    }
}