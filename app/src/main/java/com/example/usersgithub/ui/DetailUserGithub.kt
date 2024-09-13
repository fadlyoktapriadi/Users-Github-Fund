package com.example.usersgithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.usersgithub.R
import com.example.usersgithub.data.response.DetailUserResponse
import com.example.usersgithub.databinding.ActivityDetailUserGithubBinding
import com.example.usersgithub.modelview.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserGithub : AppCompatActivity() {

    lateinit var binding: ActivityDetailUserGithubBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user_github)

        supportActionBar?.hide()

        binding = ActivityDetailUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        detailUserViewModel.username = intent.extras?.getString("username").toString()

        detailUserViewModel.detailUser.observe(this) { userDetail ->
            setDataDetailUser(userDetail)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager2= findViewById<ViewPager2>(R.id.view_pager)
        val adapter = SectionsPagerAdapter(supportFragmentManager,lifecycle, data = Bundle())

        viewPager2.adapter=adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val username = intent.extras?.getString("username").toString()
        val bundle = Bundle()
        bundle.putString(intent.extras?.getString("username").toString(), username)
    }

    private fun setDataDetailUser(detailUser: DetailUserResponse) {
        binding.tvUsernameDetail.text = detailUser.login
        binding.tvNameAlias.text = detailUser.name
        binding.tvFollowing.text = "${detailUser.following} Following"
        binding.tvFollowers.text = "${detailUser.followers} Followers"
        Glide.with(this)
            .load(detailUser.avatarUrl)
            .into(binding.profileImageDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}