package com.example.kotlin_interest.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityLoginBinding
import com.example.kotlin_interest.databinding.ActivityMainBinding
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
//import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContent, LoginFragment.newInstance())
            .commit()
    }
}
