package com.example.dmario_jeans_prueba_apk.ui.Service_CRUD

import com.example.dmario_jeans_prueba_apk.ui.models.usuario
import retrofit2.Call
import retrofit2.http.*

interface Usuarioservice {
    @GET("usuario/fetch_usuario.php")
    fun getUsuario(): Call<List<usuario>>

    @GET("usuario/fetch_usuario.php/{identificacion}")
    fun getUsuario(@Path("identificacion") identificacion: String): Call<usuario>

    @POST("usuario/save_usuario.php")
    fun createUsuario(@Body usuario: usuario): Call<usuario>

    @PUT("usuario/edit_usuario.php/{identificacion}")
    fun updateUsuario(@Path("identificacion") identificacion: String, @Body usuario: usuario): Call<usuario>

    @DELETE("usuario/delete_usuario.php/{identificacion}")
    fun deleteUsuario(@Path("identificacion") identificacion: String): Call<Void>
}
