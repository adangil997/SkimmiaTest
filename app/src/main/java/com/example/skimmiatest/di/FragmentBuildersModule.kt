package com.example.skimmiatest.di

import com.example.skimmiatest.view.fragment.GalleryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.example.skimmiatest.view.activity.MainActivity

/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado para proveer los fragmentos de [MainActivity]
 * checar [ActiviyBuildersModule.contributeMainActivity] en @ContributesAndroidInjector
 */
@Module
abstract class FragmentBuildersModule{

    /**
     * Se le indica como proveer de un [GalleryFragment]
     */
    @ContributesAndroidInjector
    abstract fun contributeGalleryFragment(): GalleryFragment

}