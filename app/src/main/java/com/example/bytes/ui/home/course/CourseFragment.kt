package com.example.bytes.ui.home.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bytes.R
import com.example.bytes.databinding.FragmentCourseBinding

class CourseFragment : Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(layoutInflater, container, false)
        val root = binding.root
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}