package com.example.usersgithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersgithub.R
import com.example.usersgithub.adapter.FavoriteAdapter
import com.example.usersgithub.adapter.FollowAdapter
import com.example.usersgithub.data.response.FollowingFollowersResponseItem
import com.example.usersgithub.database.FavoriteUserGithub
import com.example.usersgithub.databinding.ActivityFavoriteUserBinding
import com.example.usersgithub.databinding.ActivityMainBinding
import com.example.usersgithub.factory.SettingModelFactory
import com.example.usersgithub.factory.ViewModelFactory
import com.example.usersgithub.modelview.FavoriteViewModel
import com.example.usersgithub.modelview.SettingViewModel
import com.example.usersgithub.preference.SettingPreferences
import com.example.usersgithub.preference.dataStore

class FavoriteUser : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingviewModel = ViewModelProvider(this, SettingModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingviewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val favoriteusermodel = obtainViewModel(this@FavoriteUser)

        favoriteusermodel.getFavoriteUser().observe(this) { userfav ->
            setDataFavorite(userfav)
        }
    }

    private fun setDataFavorite(userfav: List<FavoriteUserGithub>){
        val adapter = FavoriteAdapter()
        val items = arrayListOf<FavoriteUserGithub>()
        userfav.map {
            val item = FavoriteUserGithub(username = it.username, avatarUrl = it.avatarUrl)
            items.add(item)
        }
        adapter.submitList(items)
        binding.rvFavorite.adapter = adapter
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}