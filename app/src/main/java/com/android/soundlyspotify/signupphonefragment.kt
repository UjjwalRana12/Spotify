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
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class signupphonefragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signupphonefragment, container, false)

        val usernamephoneEditText = view.findViewById<EditText>(R.id.supusername)
        val phonenEditText = view.findViewById<EditText>(R.id.supphone)
        val signupButton = view.findViewById<Button>(R.id.cont2button)
        val usernameError = view.findViewById<TextView>(R.id.supunerror)
        val phoneError = view.findViewById<TextView>(R.id.supphnoerror)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        signupButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            val username = usernamephoneEditText.text.toString()
            val phone = phonenEditText.text.toString()
            if (username.isEmpty()) {
                loadingProgressBar.visibility = View.GONE
                usernameError.text = "Username is empty"
                usernameError.visibility = View.VISIBLE
                phoneError.visibility = View.GONE
                return@setOnClickListener
            }
             else if ( phone.isEmpty()) {
                 phoneError.text ="phone no. is empty"
                usernameError.visibility = View.GONE
                phoneError.visibility = View.VISIBLE
                loadingProgressBar.visibility = View.GONE
                return@setOnClickListener
            }

            // Retrofit call for user registration by phone
            val userAPI = RetrofitClient.userAPI
            val userphoneRegistrationRequest = PhoneRegistrationRequest(username, phone)

            val call = userAPI.registerByPhone(userphoneRegistrationRequest)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val bundle = Bundle()
                        bundle.putString("USERNAME_KEY", username)

                        val otpFragment = otpfragment()
                        otpFragment.arguments = bundle

                        val fragmentTransaction = parentFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.signupphonefrag, otpFragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    } else {
                        phoneError.text="Enter Valid Details"
                        phoneError.visibility = View.VISIBLE
                        usernameError.visibility = View.GONE
                        loadingProgressBar.visibility = View.GONE

                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("api", "API call failed: ${t.message}")
                    Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        val textbutton2 = view.findViewById<TextView>(R.id.emailtext2)
        textbutton2.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.signupphonefrag, signupfragment())
            fragmentTransaction.commit()
        }
        return view
    }
}
