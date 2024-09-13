package com.example.usersgithub.modelview

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersgithub.database.FavoriteUserGithub
import com.example.usersgithub.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoriteUser(): LiveData<List<FavoriteUserGithub>> = mFavoriteRepository.getFavoriteUser()
}