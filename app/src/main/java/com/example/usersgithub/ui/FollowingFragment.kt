package com.example.usersgithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersgithub.data.response.FollowingFollowersResponseItem
import com.example.usersgithub.databinding.FragmentFollowingBinding
import com.example.usersgithub.modelview.DetailUserViewModel

class FollowingFragment : Fragment() {

    private lateinit var username : String
    private lateinit var binding: FragmentFollowingBinding


    override fun onViewCreated(view: View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = requireActivity().intent.extras?.getString("username").toString()
        binding = FragmentFollowingBinding.bind(view)

//         RV
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        detailUserViewModel.getFollowing(username)

        detailUserViewModel.followingResponseItem.observe(viewLifecycleOwner) { following ->
            setFollowingData(following)
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setFollowingData(following: List<FollowingFollowersResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(following)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}