package com.example.dmario_jeans_prueba_apk.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dmario_jeans_prueba_apk.databinding.FragmentDashboardBinding
import com.example.dmario_jeans_prueba_apk.ui.Retrofit.Retrofit_usuario
import com.example.dmario_jeans_prueba_apk.ui.Service_CRUD.Usuarioservice
import com.example.dmario_jeans_prueba_apk.ui.models.usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var usuarioservice: Usuarioservice

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioservice = Retrofit_usuario.instance.create(Usuarioservice::class.java)

        binding.editarBtn.setOnClickListener {
            val identificacion = binding.IDInput.text.toString()
            fetchUsuario(identificacion)
        }

        binding.saveBtn.setOnClickListener {
            val nombre = binding.usernameInput.text.toString()
            val identificacion = binding.IDInput.text.toString()
            val telefono = binding.telefonoInput.text.toString()
            val contrasena = binding.passwordInput.text.toString()
            saveUsuario(usuario(nombre, identificacion, telefono, contrasena))
        }
    }

    private fun fetchUsuario(identificacion: String) {
        usuarioservice.getUsuario(identificacion).enqueue(object : Callback<usuario> {
            override fun onResponse(call: Call<usuario>, response: Response<usuario>) {
                if (response.isSuccessful) {
                    val usuario = response.body()
                    binding.usernameInput.setText(usuario?.nombre)
                    binding.IDInput.setText(usuario?.identificacion)
                    binding.telefonoInput.setText(usuario?.telefono)
                    binding.passwordInput.setText(usuario?.contrasena)
                }
            }

            override fun onFailure(call: Call<usuario>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    private fun saveUsuario(usuario: usuario) {
        usuarioservice.createUsuario(usuario).enqueue(object : Callback<usuario> {
            override fun onResponse(call: Call<usuario>, response: Response<usuario>) {
                if (response.isSuccessful) {
                    // Manejar la respuesta exitosa
                }
            }

            override fun onFailure(call: Call<usuario>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
