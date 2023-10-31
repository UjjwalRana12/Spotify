package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
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

class registeredphone : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_registeredphone, container, false)

        val button = view.findViewById<Button>(R.id.contbutton)
        button.setOnClickListener {
            // Get the phone number from the EditText
            val phoneNumberEditText = view.findViewById<EditText>(R.id.editTextText4)
            val phoneNumber = phoneNumberEditText.text.toString()

            // Check if the phone number is empty
            if (phoneNumber.isEmpty()) {
                Toast.makeText(requireContext(), "Phone number is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a ForgotPhoneNumberRequest object with the phone number
            val phoneData = ForgotPhoneNumberRequest(phoneNumber)

            // Retrofit API call
            val userAPI = RetrofitClient.userAPI
            val call = userAPI.verifyForgotPhoneNumber(phoneData)

            call.enqueue(object : Callback<ApiResponse> {
                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        // Handle successful response, navigate to otpfragment or perform necessary actions
                        val fragmentTransaction = parentFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.registeredphone, otpfragment())
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    } else {
                        // Handle unsuccessful response
                        Toast.makeText(requireContext(), "Verification failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // Handle failure
                    Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Other buttons for navigation

        val textButton = view.findViewById<TextView>(R.id.emailswitch)
        textButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.registeredphone, registeredemail())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val textButton2 = view.findViewById<TextView>(R.id.signupbutton)
        textButton2.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.registeredphone, signupfragment())
            fragmentTransaction.commit()
        }

        return view
    }
}
