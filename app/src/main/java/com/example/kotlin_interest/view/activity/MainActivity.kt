package com.example.kotlin_interest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityMainBinding
import com.example.kotlin_interest.di.component.ApplicationComponent
import com.example.kotlin_interest.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }
}
