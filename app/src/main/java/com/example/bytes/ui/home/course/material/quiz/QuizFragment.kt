package com.example.bytes.ui.home.course.material.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bytes.R
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
    private var courseId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        courseId = arguments?.getString("CourseId").toString()
    }

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
        setData(courseId, token)
    }

    @SuppressLint("ResourceAsColor")
    private fun setData(courseId: String, token: String) {
        viewModel.getQuiz(courseId, token).observe(requireActivity()) { quizResponse ->
            val quiz = quizResponse.data
            val stringStream = quiz.choice
            val choice = stringStream.split(";")
            val key = quiz.key
            with(binding) {
                tvQuestion.text = quiz.question
                answer1.text = choice[0]
                answer2.text = choice[1]
                binding.rgAnswer.setOnCheckedChangeListener { group, checkedId ->
                    val radioButton = view?.findViewById<RadioButton>(checkedId)
                    val selectedText = radioButton?.text.toString()
                    if (selectedText == key) {
//                        Toast.makeText(requireActivity(), "Jawaban anda benar", Toast.LENGTH_SHORT)
//                            .show()
                        radioButton?.setBackgroundResource(R.drawable.correct_radio_button)
                    } else {
//                        Toast.makeText(
//                            requireActivity(),
//                            "Maaf, jawaban anda salah",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        radioButton?.setBackgroundResource(R.drawable.wrong_radio_button)
                    }
                }
            }
        }
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