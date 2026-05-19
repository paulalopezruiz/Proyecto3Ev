package com.example.animalesadopcion.BBDD

import androidx.room.*

@Dao
interface UsuarioDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun registrar(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE telefono = :telefono AND password = :password")
    fun login(telefono: String, password: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE telefono = :telefono")
    fun existeTelefono(telefono: String): Usuario?
}