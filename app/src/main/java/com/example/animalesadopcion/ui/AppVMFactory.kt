package com.example.animalesadopcion.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animalesadopcion.BBDD.Repositorio

class AppVMFactory(private val repo: Repositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppVM(repo) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
