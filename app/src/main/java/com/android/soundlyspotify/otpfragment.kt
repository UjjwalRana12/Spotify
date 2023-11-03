package com.android.soundlyspotify

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.soundlyspotify.RetrofitClient.userAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.os.CountDownTimer

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class otpfragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var username: String = ""
    private var countdownTimer: CountDownTimer? = null
    private val COUNTDOWN_TIME: Long = 60000 // 60 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        username = arguments?.getString("USERNAME_KEY") ?: ""
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otpfragment, container, false)

        val phoneNumberTextView = view.findViewById<TextView>(R.id.textView5)

        phoneNumberTextView.text = param1 ?: "No phone number received"

        val editText1: EditText = view.findViewById(R.id.textView6)
        val editText2: EditText = view.findViewById(R.id.textView7)
        val editText3: EditText = view.findViewById(R.id.textView8)
        val editText4: EditText = view.findViewById(R.id.textView9)
        val invalidotp = view.findViewById<TextView>(R.id.invalidotp)


        editText1.addTextChangedListener(createTextWatcher(editText2))
        editText2.addTextChangedListener(createTextWatcher(editText3))
        editText3.addTextChangedListener(createTextWatcher(editText4))

        val button2 = view.findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            invalidotp.visibility = View.GONE
            val enteredOTP = "${editText1.text}${editText2.text}${editText3.text}${editText4.text}"
            if (enteredOTP.length == 4) {
                verifyOTP(enteredOTP)
            }
        }

        startCountdownTimer(view.findViewById(R.id.textView222))

        return view
    }

    private fun createTextWatcher(nextEditText: EditText): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0) {
                    nextEditText.requestFocus()
                }
            }
        }
    }

    private fun verifyOTP(enteredOTP: String) {
        val invalidots = view?.findViewById<TextView>(R.id.invalidotp)
        val verificationRequest = VerificationRequest(username, enteredOTP)
        userAPI.verifyUser(verificationRequest).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val accessToken = response.body()?.data?.access_token
                    if (!accessToken.isNullOrBlank()) {
                        saveTokenToPreferences(accessToken)
                        showToast("OTP Verified successfully")
                        // Next screen navigation logic here
                    } else {
                        showToast("Access token is null or empty")
                    }
                } else {

                    if (invalidots != null) {
                        invalidots.text="Invalid Otp"
                        invalidots.visibility=View.VISIBLE
                    }

                    showToast("OTP Verification failed")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showToast("API call failed. Please try again.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun saveTokenToPreferences(token: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("access_token", token)
        editor.apply()
    }

    private fun startCountdownTimer(timerTextView: TextView) {
        countdownTimer = object : CountDownTimer(COUNTDOWN_TIME, 1000) { // 1000 is the interval (1 second)
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTextView.text = "Resend Otp in $secondsRemaining seconds"
            }

            override fun onFinish() {
                timerTextView.text = "Resend Otp?"
                // Additional logic on timer completion can be added here
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel() // Cancel the timer to prevent memory leaks
    }
}
