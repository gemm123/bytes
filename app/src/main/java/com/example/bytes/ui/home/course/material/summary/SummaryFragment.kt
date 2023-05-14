package com.example.bytes.ui.home.course.material.summary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.R
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.FragmentSummaryBinding
import com.example.bytes.ui.home.course.material.quiz.QuizViewModel
import com.example.bytes.ui.home.course.material.quiz.QuizViewModelFactory
import com.example.bytes.ui.main.MainActivity

class SummaryFragment : Fragment() {
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SummaryViewModel
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
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
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
        binding.btnEnd.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setData(courseId: String, token: String) {
        viewModel.getSummary(courseId, token).observe(requireActivity()) { summaryResponse ->
            val summaries = summaryResponse.data.text
            val summary = summaries.split(";")
            with(binding) {
                tvSummary1.text = summary[0]
                tvSummary2.text = summary[1]
                tvSummary3.text = summary[2]
            }
        }
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(requireActivity())
        val courseRepository = CourseRepository(apiService)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(requireActivity(), SummaryViewModelFactory(courseRepository, userRepository)).get(
            SummaryViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}