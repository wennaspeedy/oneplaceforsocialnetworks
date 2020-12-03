package com.univap.oneplace.util

import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/*
fun getSharedPref(): SharedPreferences? {
   val context:Context = MainActivity().applicationContext


    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
return sharedPref
}*/

@Throws(IOException::class)
fun createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
    return imageFile
}
