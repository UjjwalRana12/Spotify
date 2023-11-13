package com.android.soundlyspotify.Myadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter

class ImagePagerAdapter(private val context: Context, private val imageList: List<Int>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageResource(imageList[position])

        // Set OnClickListener for the ImageView
        imageView.setOnClickListener {
            // Show a Toast when the ImageView is clicked
            Toast.makeText(context, "Item clicked at position $position", Toast.LENGTH_SHORT).show()
        }

        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
