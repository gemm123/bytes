package com.example.bytes.ui.home.course.material.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bytes.R
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.remote.retrofit.ApiService
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FirstViewModel
    private lateinit var token: String
    private var courseId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseId = arguments?.getString("CourseId").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        token = "Bearer ${viewModel.getToken()}"
        setData(courseId, token)
    }

    private fun setData(courseId: String, token: String) {
        viewModel.getMaterial(courseId, token).observe(requireActivity()) { material ->
            val data = material.data
            val url = "http://103.67.186.184:8080"
            with(binding) {
                tvMaterial1Title.text = data.title
                tvMaterial1.text = data.text
                Glide.with(requireActivity())
                    .load(url + data.image)
                    .into(ivCourseBanner)
            }
        }
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(requireActivity())
        val courseRepository = CourseRepository(apiService)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(requireActivity(), FirstViewModelFactory(courseRepository, userRepository)).get(FirstViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}