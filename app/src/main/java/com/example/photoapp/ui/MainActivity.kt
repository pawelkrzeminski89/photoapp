package com.example.photoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.photoapp.R
import com.example.photoapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null) {
            val host = NavHostFragment.create(R.navigation.nav_graph)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, host)
                .setPrimaryNavigationFragment(host).commit()
        }

        
    }


    override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this,
        R.id.fragment_container
    ).navigateUp()

}