package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupfragment : Fragment() {

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signupfragment, container, false)

        val usernameEditText = view.findViewById<EditText>(R.id.editTextText3)
        val emailEditText = view.findViewById<EditText>(R.id.editTextText4)

        val signupButton = view.findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()

            val userAPI = RetrofitClient.userAPI
            val userRegistrationRequest = EmailRegistrationRequest(username, email)

            try {
                val call = userAPI.registerByEmail(userRegistrationRequest)
                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Log.d("api", "Success")
                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.signupfrag, otpfragment())
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Log.d("api", "Unsuccessful")
                            Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("api", "API call failed: ${t.message}") // Log the reason for failure
                        Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                Log.e("api", "Exception: ${e.message}") // Log exceptions
                Toast.makeText(requireContext(), "Exception: " + e.message, Toast.LENGTH_SHORT).show()
            }
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
            fragmentTransaction.replace(R.id.signupfrag, loginfragment())
            fragmentTransaction.commit()
        }

        return view
    }
}
