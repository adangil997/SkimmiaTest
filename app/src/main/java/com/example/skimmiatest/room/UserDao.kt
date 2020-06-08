package com.example.skimmiatest.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.skimmiatest.model.User

/**
 * @author Adán Gilberto Castillo Vargas
 * @interface para proveer un Data Access Object de la tabla [User]
 */
@Dao
internal interface UserDao {

    /**
     * Se crea conflicto cuando el id del usuario coincide con otro
     * En este caso lo remplazamos
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User?)

    /**
     * Obtenemos los datos del usuario
     */
    @Query("SELECT * FROM user")
    fun getUser(): LiveData<User?>?

}