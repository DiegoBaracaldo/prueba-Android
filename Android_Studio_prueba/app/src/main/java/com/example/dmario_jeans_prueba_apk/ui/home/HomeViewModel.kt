package com.example.dmario_jeans_prueba_apk.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Diseños Fragment"
    }
    val text: LiveData<String> = _text
}