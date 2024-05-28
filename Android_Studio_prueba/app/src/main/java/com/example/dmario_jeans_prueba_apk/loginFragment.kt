package com.example.dmario_jeans_prueba_apk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dmario_jeans_prueba_apk.databinding.FragmentLogin2Binding

import com.example.dmario_jeans_prueba_apk.ui.Retrofit.Retrofit_bodega
import com.example.dmario_jeans_prueba_apk.ui.Service_CRUD.AuthService
import com.example.dmario_jeans_prueba_apk.ui.models.usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment2 : Fragment() {

    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!

    private lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authService = Retrofit_bodega.instance.create(AuthService::class.java)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val identificacion = binding.identificacioninput.text.toString()
            val cargo = binding.cargoinput.text.toString()
            val telefono = binding.telefonoinput.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && identificacion.isNotEmpty() && cargo.isNotEmpty() && telefono.isNotEmpty()) {
                val user = usuario(nombre = username, identificacion = identificacion, telefono = telefono, contrasena = password)
                loginUser(user)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(user: usuario) {
        authService.login(user).enqueue(object : Callback<usuario> {
            override fun onResponse(call: Call<usuario>, response: Response<usuario>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    // Aquí podrías navegar a otra pantalla o realizar alguna acción adicional
                } else {
                    Toast.makeText(context, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<usuario>, t: Throwable) {
                Toast.makeText(context, "Error en la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
