package com.example.dmario_jeans_prueba_apk.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Bodega Fragment"
    }
    val text: LiveData<String> = _text
}