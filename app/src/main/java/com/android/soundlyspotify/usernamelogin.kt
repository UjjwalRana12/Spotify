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
import org.w3c.dom.Text
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
        val usernotexist = view.findViewById<TextView>(R.id.usernamenotexist)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.progressBar2)


        button.setOnClickListener {
            val username = usernameEditText.text.toString()
            loadingProgressBar.visibility=View.VISIBLE
            if (username.isEmpty()) {
                loadingProgressBar.visibility=View.GONE
                usernotexist.text ="Username is empty"
                usernotexist.visibility = View.VISIBLE
                return@setOnClickListener
            }



            val userLoginRequest = UserLoginRequest(username)
            val userAPI = RetrofitClient.userAPI
            val call = userAPI.userLogin(userLoginRequest)

            call.enqueue(object : Callback<ApiResponse<Any?>> {
                override fun onResponse(call: Call<ApiResponse<Any?>>, response: Response<ApiResponse<Any?>>) {
                    if (response.isSuccessful) {

                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.success) {
                            val fragment = otpfragment()
                            val args = Bundle()
                            args.putString("USERNAME_KEY", username)
                            fragment.arguments = args

                           // Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()

                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.usernamelogin, fragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }

                        else {
                            loadingProgressBar.visibility=View.GONE
                            usernotexist.text ="Username does not exist"
                            usernotexist.visibility=View.GONE
                            usernotexist.visibility=View.VISIBLE
                        }
                    } else {
                        usernotexist.text ="Username does not exist"
                        usernotexist.visibility=View.GONE
                        usernotexist.visibility=View.VISIBLE
                        loadingProgressBar.visibility=View.GONE
                    }
                }

                override fun onFailure(call: Call<ApiResponse<Any?>>, t: Throwable) {
                    loadingProgressBar.visibility=View.GONE
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
        val textButtonA = view.findViewById<TextView>(R.id.signup2button)
        textButtonA.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.usernamelogin, signupfragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}
