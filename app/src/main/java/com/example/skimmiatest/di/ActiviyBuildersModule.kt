package com.example.skimmiatest.di

import com.example.skimmiatest.di.mainmodules.MainModule
import com.example.skimmiatest.di.profilemodules.ProfileModule
import com.example.skimmiatest.view.activity.MainActivity
import com.example.skimmiatest.view.activity.ProfileActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import android.app.Application
import com.example.skimmiatest.view.fragment.GalleryFragment
import com.example.skimmiatest.model.User
import com.example.skimmiatest.room.UserDao

/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado para proveer las actividades de [Application]
 */
@Module
internal abstract class ActiviyBuildersModule{

    /**
     * La anotacion @ContributesAndroidInjector en [contributeMainActivity] le indica
     * que los modulos con los que trabajará seran [FragmentBuildersModule] que provee [GalleryFragment]
     * y [MainModule] que provee las listas de las fotos y los sonidos
     */
    @ContributesAndroidInjector(modules = [
        FragmentBuildersModule::class,
        MainModule::class
    ])
    abstract fun contributeMainActivity(): MainActivity

    /**
     * Se le indica en @ContributesAndroidInjector en [contributeProfileActivity] indica
     * que los modulos con los que trabajará
     * seran [ProfileModule] que provee la base de datos en la que se encuentra el dao [UserDao]
     * con el que podemos acceder a la tabla [User]
     */
    @ContributesAndroidInjector(modules = [
        ProfileModule::class
    ])
    abstract fun contributeProfileActivity(): ProfileActivity

}