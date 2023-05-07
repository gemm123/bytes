package com.example.bytes.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bytes.ui.home.course.CourseFragment
import com.example.bytes.ui.home.flashcard.FlashcardFragment

class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CourseFragment()
            1 -> FlashcardFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }

}