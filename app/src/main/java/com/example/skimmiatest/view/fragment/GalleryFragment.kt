package com.example.skimmiatest.view.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.skimmiatest.R
import com.example.skimmiatest.databinding.FragmentGalleryBinding
import com.example.skimmiatest.helper.ZoomOutPageTransformer
import com.example.skimmiatest.model.Image
import com.example.skimmiatest.model.Song
import com.example.skimmiatest.services.MusicService
import com.example.skimmiatest.view.adapter.GalleryPagerAdapter
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

class GalleryFragment : DaggerDialogFragment(), MediaController.MediaPlayerControl {
    @Inject lateinit var imageList : ArrayList<Image>
    @Inject lateinit var songList : ArrayList<Song>
    private var selectedPosition: Int = 0
    private lateinit var viewPager: ViewPager
    private lateinit var galleryPagerAdapter: GalleryPagerAdapter
    private lateinit var fragmentGalleryBinding : FragmentGalleryBinding
    //service
    var musicSrv: MusicService? = null
    var playIntent: Intent? = null
    //binding
    private var musicBound = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentGalleryBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_gallery, null, false)
        viewPager = fragmentGalleryBinding.viewPager
        //imageList = arrayListHelper.imageList
        //songList = arrayListHelper.songList
        selectedPosition = arguments!!.getInt("position")
        galleryPagerAdapter = GalleryPagerAdapter(activity!!, imageList)
        fragmentGalleryBinding.image = imageList[selectedPosition]
        viewPager.adapter = galleryPagerAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        setCurrentItem(selectedPosition)
        return fragmentGalleryBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    private fun setCurrentItem(position: Int) {
        viewPager.setCurrentItem(position, false)
    }
    // viewpager page change listener
    private var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // set gallery title
                System.err.println("pager")
                fragmentGalleryBinding.image = imageList[position]
                val musicPosition : Int = position % songList.size
                songPicked(musicPosition)
            }
            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }
            override fun onPageScrollStateChanged(arg0: Int) {
            }
        }

    //connect to the service
    private val musicConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            //get service
            musicSrv = binder.service
            //pass list
            musicSrv!!.setList(songList)
            musicBound = true
            System.err.println("connection")
            val musicPosition : Int = selectedPosition % songList.size
            songPicked(musicPosition)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    //start and bind the service when the activity starts
    override fun onStart() {
        super.onStart()
        if (playIntent == null) {
            playIntent = Intent(context, MusicService::class.java)
            requireActivity().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE)
            requireActivity().startService(playIntent)
        }
    }

    //user song select
    private fun songPicked(id : Int) {
        if(musicSrv != null){
            musicSrv!!.setSong(id)
            musicSrv!!.playSong()
        }
    }

    override fun canPause(): Boolean {
        return true
    }

    override fun canSeekBackward(): Boolean {
        return true
    }

    override fun canSeekForward(): Boolean {
        return true
    }

    override fun getAudioSessionId(): Int {
        return 0
    }

    override fun getBufferPercentage(): Int {
        return 0
    }

    override fun getCurrentPosition(): Int {
        return if (musicSrv != null && musicBound && musicSrv!!.isPng()) musicSrv!!.getPosn() else 0
    }

    override fun getDuration(): Int {
        return if (musicSrv != null && musicBound && musicSrv!!.isPng()) musicSrv!!.getDur() else 0
    }

    override fun isPlaying(): Boolean {
        return if (musicSrv != null && musicBound) musicSrv!!.isPng() else false
    }

    override fun pause() {
        musicSrv!!.pausePlayer()
    }

    override fun seekTo(pos: Int) {
        musicSrv!!.seek(pos)
    }

    override fun start() {
        musicSrv!!.go()
    }

    override fun onDestroy() {
        requireActivity().stopService(playIntent)
        musicSrv = null
        super.onDestroy()
    }

}