package com.example.dmario_jeans_prueba_apk.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dmario_jeans_prueba_apk.databinding.FragmentHomeBinding
import com.example.dmario_jeans_prueba_apk.ui.Service_CRUD.Diseñoservice
import com.example.dmario_jeans_prueba_apk.ui.Retrofit.Retrofit_diseño
import com.example.dmario_jeans_prueba_apk.ui.models.diseño
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var diseñoservice: Diseñoservice

    private val PICK_PDF_REQUEST = 1
    private var pdfUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diseñoservice = Retrofit_diseño.instance.create(Diseñoservice::class.java)

        binding.ConsDisenoBtn.setOnClickListener{
            val codigo = binding.codDisenoInput.text.toString()
            fetchDiseño(codigo)
        }

        binding.saveDisenoBtn.setOnClickListener {
            val nombre = binding.namedisenoInput.text.toString()
            val codigo = binding.codDisenoInput.text.toString()
            val descripcion = binding.DescripcionInput.text.toString()
            val diseño = diseño(nombre, codigo, descripcion)
            saveDiseño(diseño)
        }

        binding.ConsDisenoBtn.setOnClickListener {
            selectPDFFile()
        }

        binding.saveDisenoBtn.setOnClickListener {
            val description = binding.fileDescriptionInput.text.toString()
            pdfUri?.let { uri -> uploadPDF(uri, description) }
        }
    }

    private fun fetchDiseño(codigo: String) {
        diseñoservice.getDiseño(codigo).enqueue(object : Callback<diseño> {
            override fun onResponse(call: Call<diseño>, response: Response<diseño>) {
                if (response.isSuccessful) {
                    val diseño = response.body()
                    binding.namedisenoInput.setText(diseño?.nombre_diseño)
                    binding.codDisenoInput.setText(diseño?.codigo)
                    binding.DescripcionInput.setText(diseño?.descripcion)
                }
            }

            override fun onFailure(call: Call<diseño>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    private fun saveDiseño(diseño: diseño) {
        diseñoservice.createDiseño(diseño).enqueue(object : Callback<diseño> {
            override fun onResponse(call: Call<diseño>, response: Response<diseño>) {
                if (response.isSuccessful) {
                    // Manejar la respuesta exitosa
                }
            }

            override fun onFailure(call: Call<diseño>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    private fun selectPDFFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(Intent.createChooser(intent, "Seleccionar PDF"), PICK_PDF_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PDF_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            data?.data?.let { uri ->
                pdfUri = uri
                // Mostrar el nombre del archivo seleccionado en el campo de descripción o algún TextView
            }
        }
    }

    private fun uploadPDF(fileUri: Uri, description: String) {
        val file = getFileFromUri(fileUri)
        file?.let {
            val descriptionPart = RequestBody.create("text/plain".toMediaTypeOrNull(), description)
            val filePart = RequestBody.create("application/pdf".toMediaTypeOrNull(), it)
            val fileToUpload = MultipartBody.Part.createFormData("file", it.name, filePart)

            val call = diseñoservice.uploadProfilePicture(descriptionPart, fileToUpload)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Manejar la respuesta exitosa
                    } else {
                        // Manejar el error en la respuesta
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Manejar el error
                }
            })
        }
    }

    private fun getFileFromUri(uri: Uri): File? {
        var file: File? = null
        context?.contentResolver?.openInputStream(uri)?.let { inputStream ->
            file = createTempFile()
            copyStreamToFile(inputStream, file!!)
        }
        return file
    }

    private fun copyStreamToFile(inputStream: InputStream, file: File) {
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
