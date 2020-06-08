package com.example.skimmiatest.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass
import com.example.skimmiatest.viewmodel.factory.ViewModelProviderFactory

/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado para asignar y detectar un mapkey de un [ViewModel]
 * en [ViewModelProviderFactory]
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)