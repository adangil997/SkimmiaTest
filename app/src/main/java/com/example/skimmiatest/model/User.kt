package com.example.skimmiatest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skimmiatest.services.MusicService
import com.example.skimmiatest.view.activity.ProfileActivity

/**
 * @author Adán Gilberto Castillo Vargas
 * @data_class de la tabla usuario que utiliza [ProfileActivity]
 * @param id es identificador unico por usuario
 * @param username es el nombre de usuario
 * @param nombre es el nombre del usuario
 * @param apellido es el apellido del usuario
 * @param biografia es la biografia del usuario
 */
@Entity(tableName = "user")
data class User (@PrimaryKey val id: Int,
                 val username: String,
                 val nombre: String,
                 val apellido: String,
                 val biografia: String)