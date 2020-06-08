package com.example.skimmiatest.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.skimmiatest.R
import com.example.skimmiatest.databinding.ImageFullscreenBinding
import com.example.skimmiatest.model.Image

class GalleryPagerAdapter(private val activity : FragmentActivity, private val imageList : ArrayList<Image>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val imageFullscreenBinding = DataBindingUtil
            .inflate<ImageFullscreenBinding>(layoutInflater, R.layout.image_fullscreen, null, false)
        val image = imageList[position]
        imageFullscreenBinding.image = image
        container.addView(imageFullscreenBinding.root)
        return imageFullscreenBinding.root
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj as View
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}