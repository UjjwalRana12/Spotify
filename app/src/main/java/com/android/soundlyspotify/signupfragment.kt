package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
        val sgnupusername = view.findViewById<TextView>(R.id.textView113)
        val sgnupemail = view.findViewById<TextView>(R.id.textView114)
        val signupButton = view.findViewById<Button>(R.id.signupButton)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingProgressBar)
        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            loadingProgressBar.visibility = View.VISIBLE
            if (username.isEmpty()) {
                sgnupusername.text="Username is empty"
                sgnupusername.visibility=View.VISIBLE
                loadingProgressBar.visibility = View.GONE

                sgnupemail.visibility=View.GONE

                return@setOnClickListener
            } else if (email.isEmpty()) {
                sgnupemail.text="Email is empty"
                sgnupusername.visibility=View.GONE
                loadingProgressBar.visibility = View.GONE
                sgnupemail.visibility=View.VISIBLE
                return@setOnClickListener

            }

            val userAPI = RetrofitClient.userAPI
            val userRegistrationRequest = EmailRegistrationRequest(username, email)

            try {
                val call = userAPI.registerByEmail(userRegistrationRequest)
                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Log.d("api", "Success")
                            loadingProgressBar.visibility = View.GONE
                            val fragment = otpfragment()
                            val bundle = Bundle()
                            bundle.putString("USERNAME_KEY", username)
                            fragment.arguments = bundle

                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.signupfrag, fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            loadingProgressBar.visibility = View.GONE
                            Log.d("api", "Unsuccessful")
                            sgnupemail.text=" Enter valid Details"
                            sgnupusername.visibility=View.GONE
                            sgnupemail.visibility=View.VISIBLE

                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("api", "API call failed: ${t.message}")
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
            fragmentTransaction.replace(R.id.signupfrag, usernamelogin())
            fragmentTransaction.commit()
        }

        return view
    }
}
