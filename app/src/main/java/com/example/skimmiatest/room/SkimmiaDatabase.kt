package com.example.skimmiatest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.skimmiatest.model.User

/**
 * @author Adán Gilberto Castillo Vargas
 * @class para proveer una instancia de la base de datos [SkimmiaDatabase]
 * a traves de la herencia de [RoomDatabase]
 * [SkimmiaDatabase.userDao] nos proporciona un objeto de acceso a los datos del usuario
 */
@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class SkimmiaDatabase : RoomDatabase() {
    internal abstract fun userDao(): UserDao?
}