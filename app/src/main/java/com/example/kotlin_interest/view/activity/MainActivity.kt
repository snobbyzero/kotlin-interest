package com.example.kotlin_interest.view.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.signature.ObjectKey
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.ActivityMainBinding
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.util.SessionManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.nav_header_main.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var sessionManager: SessionManager

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Navigation
        val navController = findNavController(R.id.nav_host_fragment)
        val drawerLayout = binding.drawerLayout
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = binding.navView
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_dialogs, R.id.nav_profile)
            .setOpenableLayout(drawerLayout)
            .build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navigationView, navController)

        user = sessionManager.getUser()!!

        val imageView = binding.navView.getHeaderView(0).userImageView
        val usernameTextView = binding.navView.getHeaderView(0).userNameTextView

        val url = "http://interest-project.herokuapp.com/user/${user.id}/image"

        Glide.with(this)
            .load(url)
            .signature(ObjectKey(sessionManager.getImageToken()!!))
            .circleCrop()
            .into(imageView)

        usernameTextView.text = user.username
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

}
