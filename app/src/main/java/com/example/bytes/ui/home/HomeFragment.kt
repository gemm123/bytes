package com.example.bytes.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().actionBar?.hide()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupViewModel()
        setupViewPagerAdapter()
        return root
    }

    private fun setupViewPagerAdapter() {
        val adapter = ViewPagerAdapter(requireActivity())
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Course"
                1 -> "Flashcard"
                else -> null
            }
        }.attach()
    }

    private fun setupViewModel() {
        val homeViewModel =
            ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}