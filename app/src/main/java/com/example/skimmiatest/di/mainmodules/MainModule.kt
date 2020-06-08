package com.example.skimmiatest.di.mainmodules

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.skimmiatest.model.Image
import com.example.skimmiatest.model.Song
import dagger.Module
import dagger.Provides
import com.example.skimmiatest.view.activity.MainActivity
import com.example.skimmiatest.view.fragment.GalleryFragment
import com.example.skimmiatest.di.AppComponent

/**
 * @author Adán Gilberto Castillo Vargas
 * Modulo dagger utilizado por [MainActivity]
 */
@Module
class MainModule {

    /**
     * Se crea cuando [MainActivity] y [GalleryFragment] lo requieren a traves de la anotación inject con ayuda de dagger
     * @param application obtenido de [AppComponent]
     * @return [ArrayList] de las imagenes del dispositivo
     */
    @Provides
    fun providesImageList(application: Application): ArrayList<Image> {
        val imageList = ArrayList<Image>()
        var i = 0
        val resolver = application.contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (i < cursor.count) {
                cursor.moveToPosition(i)
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val folder =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                val photo = Image(imageUri.toString(), folder)
                imageList.add(photo)
                i++
            }
            cursor.close()
        }
        //Ordenamos las imagenes alfabeticamente
        imageList.sortWith(Comparator { a, b -> a!!.title!!.compareTo(b!!.title!!) })
        return imageList
    }

    /**
     * Se crea cuando [MainActivity] lo requiere a traves de la anotación inject con ayuda de dagger
     * @return [ArrayList] de la musica del dispositivo
     */
    @Provides
    fun providesSongList(application: Application): ArrayList<Song> {
        val songList = ArrayList<Song>()
        val musicResolver : ContentResolver = application.contentResolver
        val musicUri : Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val musicCursor : Cursor? = musicResolver.query(musicUri, null, null, null, null)
        if(musicCursor != null && musicCursor.moveToFirst()){
            val idColumn : Int = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val titleColumn : Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artistColumn : Int = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            do {
                val id : Long = musicCursor.getLong(idColumn)
                val title : String = musicCursor.getString(titleColumn)
                val artist : String = musicCursor.getString(artistColumn)
                songList.add(Song(id, title, artist))
            }while (musicCursor.moveToNext())
        }
        //Ordenamos la musica alfabeticamente
        songList.sortWith(Comparator { a, b -> a!!.title!!.compareTo(b!!.title!!) })
        musicCursor?.close()
        return songList
    }

}
