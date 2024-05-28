package com.example.dmario_jeans_prueba_apk.ui.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit_diseño {
    private const val BASE_URL = "http://localhost/prueba/APIs%20Android/diseño/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}