package com.example.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calendar.monthly.MonthlyFragment
import com.example.calendar.weekly.WeeklyFragment
import com.example.calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initBottomNavigation()
        setContentView(binding.root)
    }

    // 바텀 네비게이션 구현
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, MonthlyFragment())
            .commitAllowingStateLoss()

        binding.mainBottomNavBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.bottom_nav_monthly -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MonthlyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.bottom_nav_weekly -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, WeeklyFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}