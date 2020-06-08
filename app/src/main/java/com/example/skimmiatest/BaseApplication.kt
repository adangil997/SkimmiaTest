package com.example.skimmiatest

import com.example.skimmiatest.di.DaggerAppComponent
import com.example.skimmiatest.model.User
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * @author Adán Gilberto Castillo Vargas
 * @class que crea los modulos de dagger necesarios para toda la aplicacion
 */
class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}