package com.example.kotlin_rgr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_rgr.fragments.MainFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.placeholder,MainFragment.newInstance()).commit()
    }
}