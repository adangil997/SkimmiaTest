package com.example.skimmiatest.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skimmiatest.R
import com.example.skimmiatest.interfaces.ImageClickListener
import com.example.skimmiatest.model.Image
import com.example.skimmiatest.view.adapter.ImageAdapter
import com.example.skimmiatest.view.fragment.GalleryFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ImageClickListener {
    @Inject lateinit var imageList : ArrayList<Image>
    private lateinit var galleryAdapter: ImageAdapter
    private var galleryFragment = GalleryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init adapter
        galleryAdapter = ImageAdapter(imageList)
        galleryAdapter.listener = this
        // init recyclerview
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = galleryAdapter
    }

    override fun onClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        galleryFragment = GalleryFragment()
        galleryFragment.arguments = bundle
        galleryFragment.show(fragmentTransaction, "gallery")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //menu item selected
        when(item.itemId){
            R.id.action_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.action_end -> {
                stopService(galleryFragment.playIntent)
                galleryFragment.musicSrv = null
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}