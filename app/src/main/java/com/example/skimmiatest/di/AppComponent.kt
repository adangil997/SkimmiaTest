package com.example.skimmiatest.di

import android.app.Application
import com.example.skimmiatest.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import com.example.skimmiatest.di.mainmodules.MainModule

/**
 * @author Adán Gilberto Castillo Vargas
 * @Component dagger señalado con la anotación
 * @Singleton para hacer referencia a todos los modulos
 * que deben de tener un ciclo de vida a nivel de la aplicacion
 * en este caso las actividades y los viewmodels disponibles
 */
@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ActiviyBuildersModule::class),
    (ViewModelFactoryModule::class)
])
internal interface AppComponent : AndroidInjector<BaseApplication> {

    /**
     * Interface para construir el componente
     */
    @Component.Builder
    interface Builder{

        /**
         * Enlaza la aplicacion a nivel de aplicacion para
         * obtener un contexto en donde se requiera
         * por ejemplo en [MainModule.providesImageList] y [MainModule.providesSongList]
         */
        @BindsInstance
        fun application(application: Application):Builder

        fun build() : AppComponent

    }

}