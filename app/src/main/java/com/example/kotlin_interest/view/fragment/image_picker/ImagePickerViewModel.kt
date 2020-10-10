package com.example.kotlin_interest.view.fragment.image_picker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import com.example.kotlin_interest.retrofit.ImageRetrofitService
import com.example.kotlin_interest.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.URI
import java.util.*
import javax.inject.Inject

class ImagePickerViewModel @Inject constructor(
    private val imageRetrofitService: ImageRetrofitService,
    private val sessionManager: SessionManager
) : ViewModel() {

    suspend fun saveImage(inputStream: InputStream) {
        val imageToken = UUID.randomUUID().toString()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true)
        val bos = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), bos.toByteArray())
        val body = MultipartBody.Part.createFormData("file", "name", requestFile)
        val userId = sessionManager.getUser()!!.id
        withContext(Dispatchers.IO) {
            imageRetrofitService.postSaveImage(userId, imageToken, body)
            sessionManager.saveImageToken(imageToken)
        }
    }
}