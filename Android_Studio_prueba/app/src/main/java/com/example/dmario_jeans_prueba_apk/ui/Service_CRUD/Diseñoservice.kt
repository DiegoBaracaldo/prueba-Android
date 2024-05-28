package com.example.dmario_jeans_prueba_apk.ui.Service_CRUD

import com.example.dmario_jeans_prueba_apk.ui.models.diseño
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Multipart
import retrofit2.http.Part

interface Diseñoservice {

    //metodo para listar
    @GET ("diseño/fetch_diseño.php")
    fun getDiseño (): Call<List<diseño>>

    //metodo para listar con codigo
    @GET ("diseño/fetch_diseño.php/{codigo}")
    fun getDiseño (@Path("codigo") codigo: String): Call<diseño>

    //metodo para agregar
    @POST ("diseño/save_diseño.php")
    fun createDiseño (@Body diseño: diseño): Call<diseño>

    //metodo para editar
    @PUT ("diseño/edit_diseño.php/{codigo}")
    fun updateDiseño (@Path("codigo")codigo: String): Call<diseño>

    //metodo para eliminar
    @DELETE ("diseño/delete_diseño.php/{codigo}")
    fun deleteDiseño (@Path("codigo")codigo: String): Call<Void>

    //metodo para el archivo

    @Multipart
    @POST("diseño/save_diseño.php")
    fun uploadProfilePicture(
        @Part("archivo") archivo: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<Void>

    companion object
}