package com.example.kotlin_rgr.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VAdapter(fa:FragmentActivity,private  val list: List<Fragment>) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
       return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}