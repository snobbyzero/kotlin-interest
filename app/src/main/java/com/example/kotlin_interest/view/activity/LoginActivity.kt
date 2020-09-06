package com.example.kotlin_interest.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityLoginBinding
import com.example.kotlin_interest.databinding.ActivityMainBinding
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import com.example.kotlin_interest.view.fragment.login.LoginInfo
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LoginActivity () :
    DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    @Inject lateinit var loginRetrofitService: LoginRetrofitService
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            loginRetrofitService.postCreateAuthToken(LoginInfo("test", "test"), "testfinger")
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContent, LoginFragment.newInstance())
            .commit()
    }

    override fun onBackPressed() {
        when (supportFragmentManager.backStackEntryCount) {
            0 -> moveTaskToBack(true)
            else -> super.onBackPressed()
        }
    }
}
