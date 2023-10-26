import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.soundlyspotify.R
import android.widget.Button
import com.android.soundlyspotify.loginfragment
import kotlinx.android.synthetic.main.activity_main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val button = findViewById<Button>(R.id.startbutton)

        button.setOnClickListener {
            val loginFragment = loginfragment()
            fragmentTransaction.replace(R.id.container, loginFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}
