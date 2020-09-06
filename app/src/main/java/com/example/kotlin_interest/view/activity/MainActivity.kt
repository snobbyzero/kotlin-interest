package com.example.kotlin_interest.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityMainBinding
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.NetworkUtil
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.dialogs.DialogsFragment
import com.example.kotlin_interest.view.fragment.home.HomeFragment
import com.example.kotlin_interest.view.fragment.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    @Inject
    lateinit var networkUtil: NetworkUtil

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment.newInstance(), HomeFragment::class.java.simpleName)
        }

        // Navigation
        val bottomNavigationView = binding.navView
        val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_profile -> {
                    ProfileFragment.newInstance()
                }
                R.id.nav_home -> {
                    HomeFragment.newInstance()
                }
                R.id.nav_dialogs -> {
                    DialogsFragment.newInstance()
                }
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, fragment::class.java.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            return@OnNavigationItemSelectedListener false
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
    }


    override fun onBackPressed() {
        when(supportFragmentManager.findFragmentById(R.id.fragmentContent)) {
            is HomeFragment -> moveTaskToBack(true)
            is ProfileFragment -> moveTaskToBack(true)
            is DialogsFragment -> moveTaskToBack(true)
            else -> return
        }
    }
}
