package com.example.bytes.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.bytes.data.remote.response.DataItem

class CourseDiffCallback(private val oldCourseList: ArrayList<DataItem>, private val newCourseList: ArrayList<DataItem>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldCourseList.size
    }

    override fun getNewListSize(): Int {
        return newCourseList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCourseList[oldItemPosition].title == newCourseList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCourse = oldCourseList[oldItemPosition]
        val newCourse = newCourseList[newItemPosition]
        return (
                oldCourse.title == newCourse.title &&
                oldCourse.tag == newCourse.tag &&
                oldCourse.description == newCourse.description &&
                oldCourse.thumbnail == newCourse.thumbnail &&
                oldCourse.userLike == newCourse.userLike)
    }
}