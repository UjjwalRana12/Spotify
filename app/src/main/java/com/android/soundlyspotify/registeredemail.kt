package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class registeredemail : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registeredemail, container, false)

        val emailView = view.findViewById<EditText>(R.id.editemailTextText4)
        val navButton = view.findViewById<Button>(R.id.cont8button)

        navButton.setOnClickListener {
            val emailInput = emailView.text.toString()
            if (emailInput.isEmpty()) {
                Toast.makeText(requireContext(), "Email is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Retrofit API call
            val userAPI = RetrofitClient.userAPI
            val useremailRegistrationRequest = ForgotEmailVerificationRequest(emailInput)

            val call = userAPI.verifyForgotEmail(useremailRegistrationRequest)
            call.enqueue(object : Callback<ApiResponse> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d("api", "Success")
                        val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.registeredemail, otpfragment())
                            fragmentTransaction.addToBackStack(null) // Add transaction to back stack
                            fragmentTransaction.commit()
                    } else {
                        Log.d("api", "Unsuccessful")
                        Toast.makeText(requireContext(), "Enter valid details.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("api", "API call failed: ${t.message}")
                    Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })
        }


        val textButton = view.findViewById<TextView>(R.id.phoneswitch)
        textButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.registeredemail, registeredphone())

            fragmentTransaction.commit()
        }
//        val button = view.findViewById<Button>(R.id.contbutton)
//        button.setOnClickListener {
//            val fragmentTransaction = parentFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.registeredemail,otpfragment())
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//        }
        val textButton2 = view.findViewById<TextView>(R.id.signupbutton)
        textButton2.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.registeredemail, signupfragment())

            fragmentTransaction.commit()
        }

        return view
    }
}
