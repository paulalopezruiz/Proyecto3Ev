package com.example.animalesadopcion.BBDD

import androidx.room.*

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registrar(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre AND password = :password")
    fun login(nombre: String, password: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    fun existeNombre(nombre: String): Usuario?
}
