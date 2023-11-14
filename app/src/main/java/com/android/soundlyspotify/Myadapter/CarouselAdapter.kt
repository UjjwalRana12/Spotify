import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.soundlyspotify.R

class CarouselAdapter(private val context: Context) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    private val images = intArrayOf(
        R.drawable.crousel1,
        R.drawable.crousel2,
        R.drawable.crousel3,
        R.drawable.crousel4
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewClouser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crousel_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])

        // Set click listener on the image
        holder.imageView.setOnClickListener {
            // Show a toast when the image is clicked
            Toast.makeText(context, "Image clicked at position $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
