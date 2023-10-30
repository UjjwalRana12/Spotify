
package com.android.soundlyspotify

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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class signupphonefragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signupphonefragment, container, false)

        val usernamephoneEditText = view.findViewById<EditText>(R.id.supusername)
        val phonenEditText = view.findViewById<EditText>(R.id.supphone)

        val signupButton = view.findViewById<Button>(R.id.cont2button)
         signupButton.setOnClickListener {
            val username = usernamephoneEditText.text.toString()
            val phone = phonenEditText.text.toString()
            if (username.isEmpty()) {
                Toast.makeText(requireContext(), "Username is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            else if (phone.isEmpty()) {
                Toast.makeText(requireContext(), "Phone Number is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userAPI = RetrofitClient.userAPI
            val userphoneRegistrationRequest = PhoneRegistrationRequest(username, phone)

            try {
                val call = userAPI.registerByPhone(userphoneRegistrationRequest)
                call.enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            Log.d("api", "Success")
                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.signupphonefrag, otpfragment())
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        } else {
                            Log.d("api", "Unsuccessful")
                            Toast.makeText(requireContext(), "enter valid details.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                        Log.e("api", "API call failed: ${t.message}")
                        Toast.makeText(requireContext(), "API call failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                Log.e("api", "Exception: ${e.message}")
                Toast.makeText(requireContext(), "Exception: " + e.message, Toast.LENGTH_SHORT).show()
            }
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