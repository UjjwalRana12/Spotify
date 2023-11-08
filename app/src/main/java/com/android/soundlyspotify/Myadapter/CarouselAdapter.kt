package com.android.soundlyspotify.Myadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.android.soundlyspotify.R

class CarouselAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(
        R.drawable.closureek,
        R.drawable.closureek,
        R.drawable.closureek,
        R.drawable.closureek,
        R.drawable.closureek
    )

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.crousel_layout, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageViewClouser)
        imageView.setImageResource(images[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
