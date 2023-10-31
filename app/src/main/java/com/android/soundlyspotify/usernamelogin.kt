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

class usernamelogin : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_usernamelogin, container, false)

        val usernameEditText = view.findViewById<EditText>(R.id.userTextText4)
        val button = view.findViewById<Button>(R.id.contbutton)

        button.setOnClickListener {
            val username = usernameEditText.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userLoginRequest = UserLoginRequest(username)
            val userAPI = RetrofitClient.userAPI
            val call = userAPI.userLogin(userLoginRequest)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.success) {
                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.usernamelogin, otpfragment())
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        val textButton = view.findViewById<TextView>(R.id.forgotView2)
        textButton.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.usernamelogin, registeredemail())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}
