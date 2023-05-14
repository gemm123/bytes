package com.example.bytes.ui.home.course

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bytes.adapter.CourseAdapter
import com.example.bytes.data.locale.AuthPreference
import com.example.bytes.data.remote.response.DataItem
import com.example.bytes.data.remote.retrofit.ApiConfig
import com.example.bytes.data.repository.CourseRepository
import com.example.bytes.data.repository.UserRepository
import com.example.bytes.databinding.FragmentCourseBinding
import com.example.bytes.ui.home.course.material.MaterialActivity

class CourseFragment : Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseViewModel
    private lateinit var token: String
    private lateinit var adapter: CourseAdapter
    private lateinit var courseData: ArrayList<DataItem>
    private var isLiked = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(layoutInflater, container, false)
        val root = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        token = "Bearer ${viewModel.getToken()}"
        adapter = CourseAdapter(viewModel, token)
        setupAction()
        getListCourse()

    }

    private fun setupAction() {
        adapter.setOnItemClickCallback(object: CourseAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {
                val intent = Intent(requireActivity(), MaterialActivity::class.java)
                intent.putExtra("EXTRA_ID", data.id)
                startActivity(intent)
            }
        })

//        adapter.apply {
//            setOnLikeClickCallback(object: CourseAdapter.LikeClickListener {
//                override fun onLikeClick(dataItem: DataItem) {
//                    Toast.makeText(requireActivity(), dataItem.userLike.toString(), Toast.LENGTH_SHORT).show()
//                    isLiked = dataItem.userLike
//                    if (isLiked) {
//                        viewModel.unlikeCourse(token, dataItem.id)
//                        isLiked = false
//                    } else {
//                        viewModel.likeCourse(token, dataItem.id)
//                        isLiked = true
//                    }
//                    notifyDataSetChanged()
//                }
//            })
//        }
    }

    private fun getListCourse() {
        val layoutManager = LinearLayoutManager(requireActivity())
        with (binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
        }
        viewModel.getListCourse(token).observe(requireActivity()) { courses ->
            courseData = courses.data
            adapter.setListCourse(courseData)
        }
        val snapHelper = PagerSnapHelper()
        if (binding.recyclerView.onFlingListener == null) {
            snapHelper.attachToRecyclerView(binding.recyclerView)
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val itemCount = layoutManager.itemCount
                if (lastVisibleItem == itemCount - 1) {
                    binding.tvSeeMore.visibility = View.VISIBLE
                } else {
                    binding.tvSeeMore.visibility = View.GONE
                }
            }
        })

        binding.tvSeeMore.setOnClickListener{
            binding.recyclerView.smoothScrollToPosition(0)
            viewModel.getListCourse(token).observe(requireActivity()) { courses ->
                courseData = courses.data
                adapter.setListCourse(courseData)
            }
        }

    }
//    fun refreshRecyclerView() {
//        adapter.setListCourse(courseData)
//    }

    private fun setupViewModel() {
        val apiService = ApiConfig.getApiService()
        val authPreferences = AuthPreference(requireActivity())
        val courseRepository = CourseRepository(apiService)
        val userRepository = UserRepository(apiService, authPreferences)
        viewModel = ViewModelProvider(requireActivity(), CourseViewModelFactory(courseRepository, userRepository)).get(CourseViewModel::class.java)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}