package com.example.dmario_jeans_prueba_apk.ui.Service_CRUD

import com.example.dmario_jeans_prueba_apk.ui.models.usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("usuario/login")
    fun login(@Body user: usuario): Call<usuario>
}