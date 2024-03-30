package com.example.calendar.monthly

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendar.databinding.FragmentMonthlyBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MonthlyFragment : Fragment() {

    private lateinit var binding : FragmentMonthlyBinding
    private lateinit var standardDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthlyBinding.inflate(layoutInflater)

        standardDate = LocalDate.now()

        monthCalendar()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthCalendar() {

        binding.monthlyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
        updateCalendarViews()

        binding.monthlyBackIv.setOnClickListener {
            standardDate = standardDate.minusMonths(1)
            binding.monthlyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
            updateCalendarViews()
        }

        binding.monthlyForwardIv.setOnClickListener {
            standardDate = standardDate.plusMonths(1)
            binding.monthlyYearTv.text = "${yearFromDate(standardDate)}년 ${monthFromDate(standardDate)}월"
            updateCalendarViews()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCalendarViews() {

        val dayList = dayInMonthArray()
        Log.d("dayList", dayList.toString())
        val dayListManager = GridLayoutManager(activity, 7)
        val dayListAdapter = MonthlyDayRVAdapter(dayList)
        binding.monthlyDayListRv.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(): MutableList<MonthDate> {
        var yearMonth = YearMonth.from(standardDate)
        val dayList = ArrayList<MonthDate>()

        // 해당 월의 마지막 날짜 가져오기(결과: 1월이면 31)
        var lastDay = yearMonth.lengthOfMonth()
        // 해당 월의 첫번째 날 가져오기(결과: 2023-01-01)
        var firstDay = standardDate.withDayOfMonth(1)
        // 첫 번째날 요일 가져오기(결과: 월 ~일이 1~7에 대응되어 나타남)
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..42) {
            if (dayOfWeek == 7) { // 그 달의 첫날이 일요일 일 때 작동: 한칸 아래 줄부터 날짜 표시 되는 현상 막기 위해
                if (i > lastDay) {
                    break
                }
                else {
                    dayList.add(MonthDate(LocalDate.of(standardDate.year, standardDate.monthValue, i)))
                }
            }
            else if (i <= dayOfWeek) { // 끝에 빈칸 자르기 위해
                dayList.add(MonthDate(null))
            } else if (i > (lastDay + dayOfWeek)) {// 끝에 빈칸 자르기 위해
                break
            }

            else {
                dayList.add(MonthDate(LocalDate.of(standardDate.year, standardDate.monthValue, i - dayOfWeek)))
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