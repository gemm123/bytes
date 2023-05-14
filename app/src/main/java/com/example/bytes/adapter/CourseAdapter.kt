package com.example.bytes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bytes.R
import com.example.bytes.data.remote.response.DataItem
import com.example.bytes.databinding.CourseRowBinding
import com.example.bytes.helper.CourseDiffCallback
import com.example.bytes.ui.home.course.CourseViewModel

class CourseAdapter(private val viewModel: CourseViewModel, private val token: String): RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    private val listCourse = ArrayList<DataItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setListCourse(listCourse: ArrayList<DataItem>) {
        val diffCallback = CourseDiffCallback(this.listCourse, listCourse)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listCourse.clear()
        this.listCourse.addAll(listCourse)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class CourseViewHolder(private val binding: CourseRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(dataItem: DataItem) {
            val category: String = dataItem.tag.toString()
            val url = "http://103.67.186.184:8080"
            with(binding) {
                tvTitleCourse.text = dataItem.title
                tvCategory1.text = dataItem.tag
                when (category) {
                    "Web" -> tvCategory1.setBackgroundResource(R.drawable.web_rounded_corner)
                    "Mobile" -> tvCategory1.setBackgroundResource(R.drawable.mobile_rounded_corner)
                    "Data" -> tvCategory1.setBackgroundResource(R.drawable.data_rounded_corner)
                }
                tvDescriptionCourse.text = dataItem.description
                Glide.with(itemView.context)
                    .load(url + dataItem.thumbnail)
                    .into(ivCourse)
                btnStart.setOnClickListener {
                    onItemClickCallback?.onItemClicked(dataItem)
                }
                var isLiked = dataItem.userLike
                if (isLiked) {
                    btnLike.setImageResource(R.drawable.ic_favorite)
                }
                btnLike.setOnClickListener{
                    isLiked = !isLiked
                    if (isLiked) {
                        btnLike.setImageResource(R.drawable.ic_favorite)
                        // send like
                        viewModel.likeCourse(token, dataItem.id)
                    } else {
                        btnLike.setImageResource(R.drawable.ic_favorite_border)
                        // delete like
                        viewModel.unlikeCourse(token, dataItem.id)
                    }
//                    likeClickListener?.onLikeClick(dataItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CourseRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun getItemCount(): Int = this.listCourse.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(listCourse[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

}