package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
        val progressbarmera = view.findViewById<ProgressBar>(R.id.progressBar3)
        val button = view.findViewById<Button>(R.id.contbutton)
        button.setOnClickListener {
            progressbarmera.visibility=View.VISIBLE
            val phoneNumberEditText = view.findViewById<EditText>(R.id.editTextText4)
            val phoneNumber = phoneNumberEditText.text.toString()
            val phonebharo = view.findViewById<TextView>(R.id.text13View13)


            if (phoneNumber.isEmpty()) {
                phonebharo.text="Phone Number Required"
                phonebharo.visibility=View.VISIBLE
                progressbarmera.visibility=View.GONE
                return@setOnClickListener
            }

            if (!isValidPhoneNumber(phoneNumber)) {
                progressbarmera.visibility=View.GONE
                phonebharo.text="Invalid phone number format"
                phonebharo.visibility=View.VISIBLE

                Toast.makeText(requireContext(), "Invalid phone number format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val phoneData = ForgotPhoneNumberRequest(phoneNumber)

            val userAPI = RetrofitClient.userAPI
            val call = userAPI.verifyForgotPhoneNumber(phoneData)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val fragmentTransaction = parentFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.registeredphone, otpfragment())
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    } else {
                        progressbarmera.visibility=View.GONE
                        phonebharo.text="enter valid phone number"
                        phonebharo.visibility=View.VISIBLE
                        Toast.makeText(requireContext(), "Api call failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    progressbarmera.visibility=View.GONE
                    Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })
        }


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

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {

        // Basic check for 10 digits - you may need to adjust this based on your requirements
        val regex = Regex("\\d{10}") // Change this regex pattern according to your phone number format
        return regex.matches(phoneNumber)
    }
}