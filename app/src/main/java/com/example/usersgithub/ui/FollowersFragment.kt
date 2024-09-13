package com.example.usersgithub.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.usersgithub.data.response.FollowingFollowersResponseItem
import com.example.usersgithub.databinding.FragmentFollowersBinding
import com.example.usersgithub.modelview.DetailUserViewModel


class FollowersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var username : String
    private lateinit var binding: FragmentFollowersBinding

    override fun onViewCreated(view: View, savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = requireActivity().intent.extras?.getString("username").toString()
        binding = FragmentFollowersBinding.bind(view)

        binding.rvFollowers.layoutManager = LinearLayoutManager(requireActivity())

        val detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailUserViewModel::class.java)

        detailUserViewModel.getFollowers(username)

        detailUserViewModel.followersResponseItem.observe(viewLifecycleOwner) { followers ->
            setFollowingData(followers)
        }

        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setFollowingData(followers: List<FollowingFollowersResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(followers)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}