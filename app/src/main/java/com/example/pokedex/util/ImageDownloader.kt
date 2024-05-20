package com.example.pokedex.util

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.OutputStream

class ImageDownloader(private val activity: Activity) {

    private val STORAGE_PERMISSION_CODE = 1

    fun downloadImage(imageUrl: String) {
        val permissions = mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED ||
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED)) {
            fetchImage(imageUrl)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                permissions.toTypedArray(),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun fetchImage(url: String) {
        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    saveImageToGallery(it)
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(activity, "Failed to download the image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }
        })
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentResolver = activity.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Pokemons")
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            var outputStream: OutputStream? = null
            try {
                outputStream = contentResolver.openOutputStream(uri)
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    Toast.makeText(activity, "Image saved in the gallery", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Failed to save the image", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity, "Failed to save the image", Toast.LENGTH_SHORT).show()
            } finally {
                outputStream?.close()
            }
        }
    }

    fun handlePermissionsResult(requestCode: Int, grantResults: IntArray, imageUrl: String) {
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.isNotEmpty()
            && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            fetchImage(imageUrl)
        } else {
            Toast.makeText(activity, "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}
