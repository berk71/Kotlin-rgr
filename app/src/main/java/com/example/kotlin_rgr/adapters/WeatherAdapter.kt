package com.example.kotlin_rgr.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_rgr.Day
import com.example.kotlin_rgr.R
import com.example.kotlin_rgr.databinding.ListitemBinding
import com.squareup.picasso.Picasso

class WeatherAdapter: ListAdapter<Day,WeatherAdapter.Holder>(Comparator()) {
    class Holder(view: View ): RecyclerView.ViewHolder(view){
        val binding= ListitemBinding.bind(view)
        fun bind(item: Day)= with(binding){
            livecontair.text=item.time
            condition.text=item.condition
            temp1.text=item.CurrentTemp
            wind.text=item.wind
            windSW.text=item.windDir
            Picasso.get().load(item.imageUrl).into(im)
        }
    }
    class  Comparator : DiffUtil.ItemCallback<Day>(){
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
        return  oldItem ==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.listitem,parent,false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

}