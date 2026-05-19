package com.example.animalesadopcion.ui


import androidx.lifecycle.*
import com.example.animalesadopcion.BBDD.*
import kotlinx.coroutines.launch
import androidx.lifecycle.asLiveData


class AppVM(private val repo: Repositorio) : ViewModel() {

    val animales = repo.listarTodos().asLiveData()

    fun filtrar(tipo: String) = repo.filtrarPorTipo(tipo).asLiveData()

    fun favoritos(usuarioId: Int) = repo.listarFavoritos(usuarioId).asLiveData()

    fun adoptados(usuarioId: Int) = repo.listarAdoptados(usuarioId).asLiveData()

    fun insertar(animal: Animal) = viewModelScope.launch { repo.insertar(animal) }

    fun actualizar(animal: Animal) = viewModelScope.launch { repo.actualizar(animal) }

    fun borrar(animal: Animal) = viewModelScope.launch { repo.borrar(animal) }

    suspend fun login(tlf: String, pass: String) = repo.login(tlf, pass)

    suspend fun registrar(usuario: Usuario) = repo.registrar(usuario)
}
