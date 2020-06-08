package com.example.skimmiatest.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.skimmiatest.R
import com.example.skimmiatest.databinding.ActivityProfileBinding
import com.example.skimmiatest.model.User
import com.example.skimmiatest.room.SkimmiaDatabase
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileActivity : DaggerAppCompatActivity() {
    @Inject lateinit var skimmiaDatabase : SkimmiaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityProfileBinding : ActivityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        GlobalScope.launch {
            /*
            Cambiar los datos del usuario aquí lo reflejara en la actividad del perfil
             */
            skimmiaDatabase.userDao()!!.insertUser(User(1, "@adan.gil", "Adán Gilberto", "Castillo Vargas", "Hola mundo"))
        }
        skimmiaDatabase.userDao()!!.getUser()!!.observe(this, Observer {
            activityProfileBinding.user = it
        })
    }
}
