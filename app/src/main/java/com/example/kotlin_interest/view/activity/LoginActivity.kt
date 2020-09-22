package com.example.kotlin_interest.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.kotlin_interest.R
import com.example.kotlin_interest.database.AppDatabase
import com.example.kotlin_interest.databinding.ActivityLoginBinding
import com.example.kotlin_interest.entity.*
import com.example.kotlin_interest.model.InterestCategory
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

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
