package com.example.calendar.weekly

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.databinding.ItemWeekDayBinding

class WeeklyDayRVAdapter(private val dayList: MutableList<WeekDate>): RecyclerView.Adapter<WeeklyDayRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemWeekDayBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding : ItemWeekDayBinding = ItemWeekDayBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dayList[position].date != null) {
            holder.binding.itemDateTv.text = dayList[position].date?.dayOfMonth.toString()

            val dayOfWeekMap = mapOf(
                "SUNDAY" to "일",
                "MONDAY" to "월",
                "TUESDAY" to "화",
                "WEDNESDAY" to "수",
                "THURSDAY" to "목",
                "FRIDAY" to "금",
                "SATURDAY" to "토"
            )

            dayList[position].date?.dayOfWeek.toString().let {
                holder.binding.itemDayTv.text = dayOfWeekMap[it] ?: ""
            }
        }
    }

    override fun getItemCount(): Int = dayList.size

}