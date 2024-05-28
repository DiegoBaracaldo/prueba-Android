package com.example.dmario_jeans_prueba_apk.ui.Service_CRUD

import com.example.dmario_jeans_prueba_apk.ui.models.bodega
import retrofit2.Call
import retrofit2.http.*

interface Bodegaservice {
  //metodo general
    //metodo para listar bodega
    @GET ("bodega/fetch_bodega.php")
    fun getBodega (): Call<List<bodega>>

    //metodo para listar por codigo
    @GET ("bodega/fetch_bodega.php/{codigo}")
    fun getBodega (@Path("codigo") codigo: String):Call<bodega>

    //metodo para agregar
    @POST ("bodega/save_bodega.php")
    fun createBodega(@Body bodega: bodega):Call<bodega>

    //metodo para editar
    @PUT ("bodega/edit_bodega.php/{codigo}")
    fun updateBodega(@Path("codigo") codigo: String, @Body bodega: bodega):Call<bodega>

    //metodo para eliminar
    @DELETE ("bodega/delete_bodega.php")
    fun deleteBodega(@Path("codigo") codigo: String):Call<Void>



}