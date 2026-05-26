package com.example.animalesadopcion.BBDD

import androidx.room.*

// DAO: consultas de la tabla usuarios
@Dao
interface UsuarioDAO {

    // Inserta un usuario nuevo.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registrar(usuario: Usuario): Long

    // Busca un usuario con nombre y contraseña para iniciar sesión
    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND password = :password")
    fun login(nombre: String, password: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    fun existeNombre(nombre: String): Usuario?
}