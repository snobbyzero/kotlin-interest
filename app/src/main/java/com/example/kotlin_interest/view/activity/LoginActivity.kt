package com.example.kotlin_interest.view.activity

//import dagger.android.support.HasSupportFragmentInjector
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityLoginBinding
import com.example.kotlin_interest.retrofit.LoginRetrofitService
import com.example.kotlin_interest.util.SessionManager
import com.example.kotlin_interest.view.fragment.login.LoginFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
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
