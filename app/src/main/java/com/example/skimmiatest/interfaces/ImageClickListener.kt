package com.example.skimmiatest.interfaces

import com.example.skimmiatest.view.adapter.ImageAdapter

/**
 * @author Adán Gilberto Castillo Vargas
 * @interface para detectar el evento clic de una imagen
 * en la actividad principal a traves de [ImageAdapter]
 */
interface ImageClickListener {

    /**
     * @param position para obtener la posicion
     * es decir el elemento sobre el que se hizo clic
     */
    fun onClick(position: Int)

}