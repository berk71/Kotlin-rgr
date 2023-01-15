package com.example.kotlin_rgr.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_rgr.Day
import com.example.kotlin_rgr.MainModel
import com.example.kotlin_rgr.adapters.VAdapter
import com.example.kotlin_rgr.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val Key="3513d49373e840d2afa182354231401";
class MainFragment : Fragment() {
    private  lateinit var binding: FragmentMainBinding
    private  lateinit var  pLauncher: ActivityResultLauncher<String>
    private  val model: MainModel by activityViewModels()
    private  val fList= listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private  val TabNameList= listOf(
        "HOURS",
        "DAYS"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }
    private  fun init()= with(binding){
        val adapter= VAdapter(activity as FragmentActivity,fList)
        vp.adapter= adapter
        TabLayoutMediator(Selector,vp){
        tab,posi->tab.text=TabNameList[posi]
        }.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       checkPerm()
        init()
        requestWeather("Chernihiv")
        updateCurrentCard()
        onClickListener()

    }
    private  fun permissionLis(){
        pLauncher= registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity,"Permission is $it",Toast.LENGTH_LONG).show()
        }
    }
    private fun checkPerm(){
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionLis()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    private  fun requestWeather(city:String){
        val url="https://api.weatherapi.com/v1/forecast.json" +
                "?key=$Key&q=$city&days=3&aqi=no&alerts=no"
        val queue= Volley.newRequestQueue(context)
        val reqeust=StringRequest(
            Request.Method.GET,url,{
                    response->parseWether(response)

            },{
                error->
                Log.d("Mylog","Error:$error")
            }
        )
        queue.add(reqeust)

    }
    private fun parseWether(response: String){
        val mainobj=JSONObject(response)
        val list=parseDays(mainobj)
        parseCurrentData(mainobj,list[0])

    }
    private  fun parseCurrentData(mainobj:JSONObject,day:Day){
        val location=mainobj.getJSONObject("location")
        val current= mainobj.getJSONObject("current")
        val item= Day(
            location.getString("name"),
            location.getString("region"),
            current.getString("last_updated"),
            current.getJSONObject("condition").getString("text"),
            current.getJSONObject("condition").getString("icon"),
            day.maxTemp,
            current.getString("temp_c"),day.minTemp,current.getString("wind_mph"),
            current.getString("wind_dir"),day.hours
        )
        model.currentData.value=item
        Log.d("Mylog","Response:${item.maxTemp}:${item.hours}:${item.condition}:${item.wind}")
    }
    private  fun parseDays(mainobj: JSONObject):List<Day>{
       var list= ArrayList<Day>()
       val  daysArray=mainobj.getJSONObject("forecast").getJSONArray("forecastday")
       val name =mainobj.getJSONObject("location").getString("name")
        val region =mainobj.getJSONObject("location").getString("region")

        for(i in 0 until daysArray.length()){
            val day= daysArray[i] as JSONObject
            val item=Day(
                name,region,day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONObject("day").getString("maxtemp_c"),
                "",
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getString("maxwind_mph"),
                day.getJSONObject("day").getString("daily_chance_of_rain"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        return  list
    }
    private  fun updateCurrentCard()= with(binding){
        model.currentData.observe(viewLifecycleOwner){
            val maxmini="${it.maxTemp}C°/${it.minTemp}C°"
            CurrentData.text=it.time
            textView2.text=it.city
            Picasso.get().load("https:"+it.imageUrl).into(imageweather)
            temp.text=it.CurrentTemp+"C°"
            maxminTemp.text=maxmini

        }
    }
    private  fun onClickListener(){
        binding.refresh.setOnClickListener(){
            checkPerm()
            init()
            requestWeather("Chernihiv")
            updateCurrentCard()
            onClickListener()
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

}