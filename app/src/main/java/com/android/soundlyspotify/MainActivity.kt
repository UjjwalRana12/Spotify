package com.android.soundlyspotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstFragment = signupfragment()



        val button5 = findViewById<Button>(R.id.btnopen)


       button5.setOnClickListener{
           supportFragmentManager.beginTransaction().apply {
               replace(R.id.flFragment,firstFragment)
               addToBackStack(null)
               commit()
       }
    }

     }
}
