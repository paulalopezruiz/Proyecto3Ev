package com.example.animalesadopcion.BBDD

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDAO {

    @Query("SELECT * FROM animales")
    fun listarTodos(): Flow<List<Animal>>

    @Query("SELECT * FROM animales WHERE tipo = :tipo")
    fun filtrarPorTipo(tipo: String): Flow<List<Animal>>

    @Query("SELECT * FROM animales WHERE favoritoDe = :usuarioId")
    fun listarFavoritos(usuarioId: Int): Flow<List<Animal>>

    @Query("SELECT * FROM animales WHERE adoptadoPor = :usuarioId")
    fun listarAdoptados(usuarioId: Int): Flow<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(animal: Animal): Long

    @Update
    fun actualizar(animal: Animal): Int

    @Delete
    fun borrar(animal: Animal): Int
}