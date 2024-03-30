package com.example.calendar.monthly

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.databinding.ItemMonthDayBinding

class MonthlyDayRVAdapter(private val dayList: MutableList<MonthDate>): RecyclerView.Adapter<MonthlyDayRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMonthDayBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding : ItemMonthDayBinding = ItemMonthDayBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemDayTv.text = dayList[position].date?.dayOfMonth.toString()

        if (dayList[position].date == null) {
            holder.binding.itemDayTv.text = ""
        }
    }

    override fun getItemCount(): Int = dayList.size

}