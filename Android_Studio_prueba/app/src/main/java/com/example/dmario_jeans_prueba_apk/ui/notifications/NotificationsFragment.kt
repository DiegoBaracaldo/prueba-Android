package com.example.dmario_jeans_prueba_apk.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dmario_jeans_prueba_apk.databinding.FragmentNotificationsBinding
import com.example.dmario_jeans_prueba_apk.ui.Retrofit.Retrofit_bodega
import com.example.dmario_jeans_prueba_apk.ui.Service_CRUD.Bodegaservice
import com.example.dmario_jeans_prueba_apk.ui.models.bodega
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var bodegaservice: Bodegaservice

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bodegaservice = Retrofit_bodega.instance.create(Bodegaservice::class.java)

        binding.editarDisenoBtn.setOnClickListener {
            val codigo = binding.codDisenoInput.text.toString()
            fetchBodega(codigo)
        }

        binding.saveDisenoBtn.setOnClickListener {
            val codigo = binding.codDisenoInput.text.toString()
            val diaExacto = binding.diaInput.text.toString()
            val tipoMovimiento = binding.movimientoInput.text.toString()
            val cantidad = binding.ConsultacantidadInput.text.toString()
            saveBodega(bodega(codigo, diaExacto, tipoMovimiento, cantidad))
        }
    }

    private fun fetchBodega(codigo: String) {
        bodegaservice.getBodega(codigo).enqueue(object : Callback<bodega> {
            override fun onResponse(call: Call<bodega>, response: Response<bodega>) {
                if (response.isSuccessful) {
                    val bodega = response.body()
                    binding.codDisenoInput.setText(bodega?.codigo)
                    binding.movimientoInput.setText(bodega?.tipoMovimiento)
                    binding.diaInput.setText(bodega?.diaExacto)
                    binding.ConsultacantidadInput.setText(bodega?.cantidad)
                }
            }

            override fun onFailure(call: Call<bodega>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    private fun saveBodega(bodega: bodega) {
        bodegaservice.createBodega(bodega).enqueue(object : Callback<bodega> {
            override fun onResponse(call: Call<bodega>, response: Response<bodega>) {
                if (response.isSuccessful) {
                    // Manejar la respuesta exitosa
                }
            }

            override fun onFailure(call: Call<bodega>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
