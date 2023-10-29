
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.soundlyspotify.ApiResponse
import com.android.soundlyspotify.PhoneRegistrationRequest
import com.android.soundlyspotify.R
import com.android.soundlyspotify.RetrofitClient
import com.android.soundlyspotify.otpfragment
import com.android.soundlyspotify.signupfragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class signupphonefragment : Fragment() {
    private val userAPI = RetrofitClient.userAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signupphonefragment, container, false)

        val button = view.findViewById<Button>(R.id.cont2button)
        val usernameEditText = view.findViewById<EditText>(R.id.supusername)
        val phoneNumberEditText = view.findViewById<EditText>(R.id.supphone)

        button.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()

            if (validateData(username, phoneNumber)) {
                val phoneRegistrationRequest = PhoneRegistrationRequest(username, phoneNumber)

                userAPI.registerByPhone(phoneRegistrationRequest).enqueue(object : Callback<ApiResponse> {
                    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null && responseBody.success) {
                                // Registration successful, navigate to OTP page
                                val fragmentTransaction = parentFragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.signupphonefrag, otpfragment())
                                fragmentTransaction.addToBackStack(null)
                                fragmentTransaction.commit()
                            } else {
                                showToast("Registration failed. Please try again.")
                            }
                        } else {
                            showToast("Registration failed. Please try again.")
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {

                        showToast("Invalid Password or Username")
                    }
                })
            }
        }

        val textbutton2 = view.findViewById<TextView>(R.id.emailtext2)
        textbutton2.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.signupphonefrag, signupfragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    private fun validateData(username: String, phoneNumber: String): Boolean {
        if (username.isEmpty()) {
            showToast("Username cannot be empty")
            return false
        }

        if (phoneNumber.isEmpty()) {
            showToast("Phone number cannot be empty")
            return false
        }


        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
