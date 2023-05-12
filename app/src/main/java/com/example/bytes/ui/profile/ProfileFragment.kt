package com.example.bytes.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bytes.R
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.FragmentProfileBinding
import com.example.bytes.ui.login.LoginActivity
import com.example.bytes.ui.login.LoginViewModel
import com.example.bytes.ui.login.LoginViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.btnLogout.setOnClickListener {
            viewModel.clearToken()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(requireActivity())
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(requireActivity(), ProfileViewModelFactory(userRepository)).get(
            ProfileViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}