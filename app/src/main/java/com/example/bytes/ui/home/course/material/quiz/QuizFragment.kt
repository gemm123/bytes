package com.example.bytes.ui.home.course.material.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuizViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
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
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(requireActivity())
        val courseRepository = CourseRepository(apiService)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(requireActivity(), QuizViewModelFactory(courseRepository, userRepository)).get(
            QuizViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}