package com.example.skimmiatest.di.profilemodules

import android.app.Application
import androidx.room.Room
import com.example.skimmiatest.room.SkimmiaDatabase
import dagger.Module
import dagger.Provides
import com.example.skimmiatest.view.activity.ProfileActivity


/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado por [ProfileActivity]
 */
@Module
class ProfileModule {

    /**
     * Se crea en [ProfileActivity]
     * @return [SkimmiaDatabase] para [ProfileActivity.onCreate]
     */
    @Provides
    fun provideSkimmiaDatabase(application: Application): SkimmiaDatabase {
        return Room.databaseBuilder(
            application,
            SkimmiaDatabase::class.java, "database_skimmia"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}