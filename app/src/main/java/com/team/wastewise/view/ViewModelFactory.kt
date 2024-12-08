package com.team.wastewise.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.wastewise.data.remote.FavoriteRepository
import com.team.wastewise.data.remote.UploadRepository
import com.team.wastewise.di.Injection
import com.team.wastewise.view.detail.DetailRecommendationViewModel
import com.team.wastewise.view.favorite.FavoriteViewModel
import com.team.wastewise.view.result.ResultViewModel
import com.team.wastewise.view.upload.UploadViewModel

class ViewModelFactory(
    private val uploadRepository: UploadRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(uploadRepository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel() as T
            }
            modelClass.isAssignableFrom(DetailRecommendationViewModel::class.java) -> {
                DetailRecommendationViewModel() as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(favoriteRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(
                        Injection.provideUploadRepository(context),
                        Injection.provideFavoriteRepository(context)
                    )
                }
            }
            return instance as ViewModelFactory
        }
    }
}