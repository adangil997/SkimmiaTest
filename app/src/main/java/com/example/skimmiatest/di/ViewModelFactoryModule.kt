package com.example.skimmiatest.di

import androidx.lifecycle.ViewModelProvider
import com.example.skimmiatest.view.activity.MainActivity
import com.example.skimmiatest.viewmodel.factory.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado para proveer una instancia [ViewModelProvider.Factory]
 */
@Module
internal abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}