package com.example.calendar.weekly

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendar.databinding.FragmentWeeklyBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class WeeklyFragment : Fragment() {

    private lateinit var binding : FragmentWeeklyBinding
    private lateinit var standardDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeeklyBinding.inflate(layoutInflater)

        standardDate = LocalDate.now()

        weekCalendar()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun weekCalendar() {
        binding.weeklyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
        updateCalendarViews()

        binding.weeklyBackIv.setOnClickListener {
            standardDate = standardDate.minusMonths(1)
            binding.weeklyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
            updateCalendarViews()
        }

        binding.weeklyForwardIv.setOnClickListener {
            standardDate = standardDate.plusMonths(1)
            binding.weeklyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
            updateCalendarViews()
        }
    }

    // 주간 달력
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCalendarViews() {

        val dayList = dayInMonthArray()
        Log.d("dayList", dayList.toString())
        val dayListManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val dayListAdapter = WeeklyDayRVAdapter(dayList)
        binding.weeklyDayListRv.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(): MutableList<WeekDate> {
        var yearMonth = YearMonth.from(standardDate)
        val dayList = ArrayList<WeekDate>()

        // 해당 월의 마지막 날짜 가져오기(결과: 1월이면 31)
        var lastDay = yearMonth.lengthOfMonth()
        // 해당 월의 첫번째 날 가져오기(결과: 2023-01-01)
        var firstDay = standardDate.withDayOfMonth(1)
        // 첫 번째날 요일 가져오기(결과: 월 ~일이 1~7에 대응되어 나타남)
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..42) {
            if (i > lastDay) {
                break
            }
            else {
                dayList.add(WeekDate(LocalDate.of(standardDate.year, standardDate.monthValue, i)))
            }
        }
        return dayList
    }


    // YYYY 형식으로 포맷
    @RequiresApi(Build.VERSION_CODES.O)
    private fun yearFromDate(date: LocalDate?): String? {
        var formatter = DateTimeFormatter.ofPattern("yyyy")
        return date?.format(formatter)
    }

    // MM 형식으로 포맷
    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthFromDate(date: LocalDate?): String? {
        var formatter = DateTimeFormatter.ofPattern("MM")
        return date?.format(formatter)
    }
}