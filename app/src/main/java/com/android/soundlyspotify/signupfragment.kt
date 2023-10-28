package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class signupfragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signupfragment, container, false)

        val button = view.findViewById<Button>(R.id.cont1button)
        button.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.signupfrag,otpfragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val textbutton2 = view.findViewById<TextView>(R.id.emailtext1)
        textbutton2.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.signupfrag, signupphonefragment())

            fragmentTransaction.commit()
        }
        val textbutton = view.findViewById<TextView>(R.id.loginbutton1)
          textbutton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.signupfrag,loginfragment())
              fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}
