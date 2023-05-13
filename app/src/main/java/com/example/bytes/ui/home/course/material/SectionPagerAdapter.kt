package com.example.bytes.ui.home.course.material

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bytes.ui.home.course.material.first.FirstFragment
import com.example.bytes.ui.home.course.material.quiz.QuizFragment
import com.example.bytes.ui.home.course.material.summary.SummaryFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? =null
        when (position) {
            0 -> fragment = FirstFragment()
            1 -> fragment = QuizFragment()
            2 -> fragment = SummaryFragment()
        }
        return fragment as Fragment
    }
}