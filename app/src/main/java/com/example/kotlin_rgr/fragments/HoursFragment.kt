package com.example.kotlin_rgr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_rgr.Day
import com.example.kotlin_rgr.MainModel
import com.example.kotlin_rgr.R
import com.example.kotlin_rgr.adapters.WeatherAdapter
import com.example.kotlin_rgr.databinding.FragmentHoursBinding
import org.json.JSONArray
import org.json.JSONObject


class HoursFragment : Fragment() {

    private  lateinit var binding: FragmentHoursBinding
    private  lateinit var adapter: WeatherAdapter
    private  val model: MainModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.currentData.observe(viewLifecycleOwner){
           adapter.submitList(parseHours(it))
        }
    }
    private  fun init()= with(binding){
        rcView.layoutManager= LinearLayoutManager(activity)
        adapter= WeatherAdapter()
        rcView.adapter=adapter


    }
    private  fun parseHours(item:Day):List<Day>{
        val hoursArray=JSONArray(item.hours)
        val list=ArrayList<Day>()

        for(i in 0 until hoursArray.length()){
            val witem=Day("","",
                ( hoursArray[i]as JSONObject).getString("time"),
                ( hoursArray[i]as JSONObject).getJSONObject("condition").getString("text"),
                "https:"+( hoursArray[i]as JSONObject).getJSONObject("condition").getString("icon"),
                "",( hoursArray[i]as JSONObject).getString("temp_c"),"",
                ( hoursArray[i]as JSONObject).getString("wind_mph"),
                ( hoursArray[i]as JSONObject).getString("wind_dir"),"")
            list.add(witem)
        }
        return  list
    }

    companion object {

        @JvmStatic
        fun newInstance() = HoursFragment()

    }
}