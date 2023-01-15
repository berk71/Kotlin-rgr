package com.example.kotlin_rgr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_rgr.Day
import com.example.kotlin_rgr.R
import com.example.kotlin_rgr.adapters.WeatherAdapter
import com.example.kotlin_rgr.databinding.FragmentDaysBinding


class DaysFragment : Fragment() {
    private  lateinit var binding: FragmentDaysBinding
    private  lateinit var  adapter: WeatherAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDaysBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init()= with(binding){
        rcDays.layoutManager=LinearLayoutManager(activity)
        adapter= WeatherAdapter()
        rcDays.adapter=adapter
        val list= listOf(
            Day("","","12:00","sunny","https:\\/\\/cdn.weatherapi.com\\/weather\\/64x64\\/night\\/122.png","","-2/0 C","","16 mph","Sw",""),
            Day("","","12:00","coldy","https:\\/\\/cdn.weatherapi.com\\/weather\\/64x64\\/night\\/122.png","","0/-1 C","","16 mph","Sw",""),
            Day("","","12:00","snowing","https:\\/\\/cdn.weatherapi.com\\/weather\\/64x64\\/night\\/122.png","","4/1 C","","16 mph","Sw","")

        )
        adapter.submitList(list)

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}