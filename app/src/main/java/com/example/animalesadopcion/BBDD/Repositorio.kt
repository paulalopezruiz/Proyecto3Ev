package com.example.animalesadopcion.BBDD

class Repositorio(
    private val usuarioDAO: UsuarioDAO,
    private val animalDAO: AnimalDAO
) {

    fun listarTodos() = animalDAO.listarTodos()

    fun listarFavoritos(usuarioId: Int) = animalDAO.listarFavoritos(usuarioId)

    fun listarAdoptados(usuarioId: Int) = animalDAO.listarAdoptados(usuarioId)

    fun obtenerAnimal(id: Int) = animalDAO.obtenerAnimal(id)

    suspend fun insertar(animal: Animal) = animalDAO.insertar(animal)

    suspend fun actualizar(animal: Animal) = animalDAO.actualizar(animal)

    suspend fun borrar(animal: Animal) = animalDAO.borrar(animal)

    suspend fun login(nombre: String, pass: String) = usuarioDAO.login(nombre, pass)

    suspend fun registrar(usuario: Usuario) = usuarioDAO.registrar(usuario)
}
