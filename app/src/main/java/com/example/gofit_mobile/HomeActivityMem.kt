package com.example.gofit_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView

class HomeActivityMem : AppCompatActivity() {
    private lateinit var btmMenu: NavigationBarView
    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_mem)

        btmMenu = findViewById(R.id.bottomNav)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.layout_fragment_mem) as NavHostFragment
        btmMenu.setupWithNavController(navHostFragment.navController)
        btmMenu.selectedItemId = R.id.fragmentPresensiKelas
    }
}