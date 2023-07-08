package com.imsoft.savdodelivery.presentation.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import com.imsoft.savdodelivery.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystem()
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fragment_container) as NavHostController
        navController.navigate(R.id.loginFragment)


    }

    private fun hideSystem() {
        window?.statusBarColor = Color.TRANSPARENT
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}