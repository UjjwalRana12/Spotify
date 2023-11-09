package com.android.soundlyspotify
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.soundlyspotify.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(homefragment())

        val toolbar: Toolbar = findViewById(R.id.toolbar) // Fetching the Toolbar from the layout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Home -> {
                    replaceFragment(homefragment())
                }
                R.id.Library -> {
                    replaceFragment(library())
                }
                R.id.Game -> {
                    replaceFragment(Game())
                }
                R.id.Search -> {
                    replaceFragment(search())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }
}
