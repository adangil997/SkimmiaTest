package com.example.skimmiatest.model

/**
 * @author Adán Gilberto Castillo Vargas
 * @data_class de lo que requerimos de una imagen
 * @param id usado para reproducir una pista especifica
 * @param title titulo usado para mostrar en la notificacion del servicio [MusicService.onPrepared]
 * @param artist actualmente no usado
 */
data class Song(
    val id : Long,
    val title : String?,
    val artist : String?
)