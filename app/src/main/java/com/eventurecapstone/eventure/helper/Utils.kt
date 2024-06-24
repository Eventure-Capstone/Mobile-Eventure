package com.eventurecapstone.eventure.helper

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())
private const val MAXIMAL_SIZE = 1000000

// Function to convert Uri to MultipartBody.Part
fun getFileFromUri(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir, context.getFileName(uri))
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    outputStream.close()
    inputStream?.close()
    return file
}

@Suppress("DEPRECATION")
fun Uri.toMultipartBody(context: Context, name: String = "file"): MultipartBody.Part? {
    val file = getFileFromUri(context, this)
    val requestFile = RequestBody.create(
        "image/*".toMediaTypeOrNull(),
        file
    )
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}


// Extension function to get file name from Uri
fun Context.getFileName(uri: Uri): String {
    var name = ""
    val returnCursor: Cursor? = contentResolver.query(uri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex >= 0 && returnCursor.moveToFirst()) {
            name = returnCursor.getString(nameIndex)
        }
        returnCursor.close()
    }
    return name
}