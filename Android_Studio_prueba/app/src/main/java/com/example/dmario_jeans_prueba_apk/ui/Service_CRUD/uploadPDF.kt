package com.example.dmario_jeans_prueba_apk.ui.Service_CRUD

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

fun uploadPDF(file: File, description: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/prueba/APIs%20Android/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(Diseñoservice::class.java)

    // Crear RequestBody para la descripción y el archivo PDF
    val descriptionPart = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
    val filePart = RequestBody.create("application/pdf".toMediaTypeOrNull(), file) // Especifica el tipo MIME para PDF
    val fileToUpload = MultipartBody.Part.createFormData("file", file.name, filePart)

    // Realizar la llamada de red
    val call = apiService.uploadProfilePicture(descriptionPart, fileToUpload)
    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                // Manejar la respuesta
                println("Archivo PDF subido exitosamente")
            } else {
                println("Error en la subida del archivo PDF: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            // Manejar el error
            println("Fallo en la subida del archivo PDF: ${t.message}")
        }
    })
}
