package com.example.animalesadopcion.ui

import androidx.lifecycle.*
import com.example.animalesadopcion.BBDD.*
import kotlinx.coroutines.launch

class AppVM(private val repo: Repositorio) : ViewModel() {

    // USUARIO ACTUAL
    private val _usuarioActual = MutableLiveData<Usuario?>()
    val usuarioActual: LiveData<Usuario?> get() = _usuarioActual

    fun setUsuarioActual(usuario: Usuario?) {
        _usuarioActual.value = usuario
    }

    // LISTADOS
    val animales = repo.listarTodos().asLiveData()

    fun favoritos(usuarioId: Int) = repo.listarFavoritos(usuarioId).asLiveData()

    fun adoptados(usuarioId: Int) = repo.listarAdoptados(usuarioId).asLiveData()

    // OBTENER ANIMAL POR ID
    fun obtenerAnimal(id: Int) = repo.obtenerAnimal(id).asLiveData()

    // CRUD
    fun insertar(animal: Animal) = viewModelScope.launch { repo.insertar(animal) }

    fun actualizar(animal: Animal) = viewModelScope.launch { repo.actualizar(animal) }

    fun borrar(animal: Animal) = viewModelScope.launch { repo.borrar(animal) }

    // LOGIN / REGISTRO
    suspend fun login(nombre: String, pass: String) = repo.login(nombre, pass)

    suspend fun registrar(usuario: Usuario) = repo.registrar(usuario)
}
