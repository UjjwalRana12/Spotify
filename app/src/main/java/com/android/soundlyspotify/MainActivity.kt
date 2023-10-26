import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.soundlyspotify.R
import android.widget.Button
import com.android.soundlyspotify.loginfragment

class MainActivity : AppCompatActivity() {
    //Button btn;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
       // fragmentTransaction.replace(R.id.container)

        val loginFragment = loginfragment()
        fragmentTransaction.replace(R.id.container, loginFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


        val button = findViewById<Button>(R.id.startbutton)
        button.setOnClickListener {
            val loginFragment = loginfragment()
            fragmentTransaction.replace(R.id.container, loginFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}
