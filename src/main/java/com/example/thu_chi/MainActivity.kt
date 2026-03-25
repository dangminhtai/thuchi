package com.example.thu_chi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.thu_chi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (com.example.thu_chi.util.SecurityUtils.isLocked(this) && !com.example.thu_chi.util.SecurityUtils.isAuthenticated) {
            startActivity(Intent(this, LockActivity::class.java))
            finish()
            return
        }

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Sửa lỗi: ViewBinding chuyển bottom_navigation thành bottomNavigation
        binding.bottomNavigation.setupWithNavController(navController)
    }
}