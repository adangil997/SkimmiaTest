package com.example.skimmiatest.model

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.skimmiatest.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.example.skimmiatest.services.MusicService

/**
 * @author Adán Gilberto Castillo Vargas
 * @data_class de lo que requerimos de una imagen
 * @param imageUrl usada para obtener la ruta del archivo en el telefono
 * @param title titulo usado para mostrar el nombre de la foto
 */
data class Image(
    val imageUrl: String?,
    val title: String?
)

/**
 * @databinding se utiliza este metodo para enlazar la imagen a cada item
 * desde la vista del usuario y asi evitar errores en tiempo de ejecucion
 */
@BindingAdapter("loadFoto")
fun loadFoto(imageView: ImageView, url: String?) {
    imageView.post {
        val context = imageView.context
        val shotLoadingPlaceholders = arrayOf(
            ColorDrawable(
                ContextCompat.getColor(
                    context,
                    R.color.image_placeholder_background_color
                )
            )
        )
        Picasso
            .with(context)
            .load(url)
            .placeholder(shotLoadingPlaceholders[0])
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .resize(imageView.width, imageView.height)
            .centerCrop()
            .into(imageView)
    }
}