package com.example.dmario_jeans_prueba_apk.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Edicion de perfil Fragment"
    }
    val text: LiveData<String> = _text
}