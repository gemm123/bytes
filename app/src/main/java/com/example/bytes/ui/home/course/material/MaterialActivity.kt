package com.example.bytes.ui.home.course.material

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.bytes.databinding.ActivityMaterialBinding
import com.example.bytes.ui.main.MainActivity

class MaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaterialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager()
        setupAction()
    }

    private fun setupAction() {
        binding.btnClose.setOnClickListener {
            val intent = Intent(this@MaterialActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
            finish()
        }
    }

    private fun setupViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(this@MaterialActivity)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> progress1()
                    1 -> progress2()
                    2 -> progress3()
                }
            }
        })
    }

    private fun progress1() {
        binding.ivProgress1.visibility = View.VISIBLE
        binding.ivProgress2.visibility = View.INVISIBLE
        binding.ivProgress3.visibility = View.INVISIBLE
    }

    private fun progress2() {
        binding.ivProgress1.visibility = View.INVISIBLE
        binding.ivProgress2.visibility = View.VISIBLE
        binding.ivProgress3.visibility = View.INVISIBLE
    }

    private fun progress3() {
        binding.ivProgress1.visibility = View.INVISIBLE
        binding.ivProgress2.visibility = View.INVISIBLE
        binding.ivProgress3.visibility = View.VISIBLE
    }
}