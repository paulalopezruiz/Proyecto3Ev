package com.example.animalesadopcion.BBDD

class Repositorio(
    private val usuarioDAO: UsuarioDAO,
    private val animalDAO: AnimalDAO
) {

    // USUARIOS
    suspend fun registrar(usuario: Usuario) = usuarioDAO.registrar(usuario)
    suspend fun login(nombre: String, pass: String) = usuarioDAO.login(nombre, pass)
    suspend fun existeNombre(nombre: String) = usuarioDAO.existeNombre(nombre)

    // ANIMALES
    fun listarTodos() = animalDAO.listarTodos()
    fun filtrarPorTipo(tipo: String) = animalDAO.filtrarPorTipo(tipo)
    fun listarFavoritos(id: Int) = animalDAO.listarFavoritos(id)
    fun listarAdoptados(id: Int) = animalDAO.listarAdoptados(id)

    suspend fun insertar(animal: Animal) = animalDAO.insertar(animal)
    suspend fun actualizar(animal: Animal) = animalDAO.actualizar(animal)
    suspend fun borrar(animal: Animal) = animalDAO.borrar(animal)
}
