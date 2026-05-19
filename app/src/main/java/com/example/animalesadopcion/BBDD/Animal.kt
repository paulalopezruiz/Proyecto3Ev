package com.example.animalesadopcion.BBDD

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animales")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val sexo: String,
    val estado: String,
    val localizacion: String,
    val imagen: Int,
    val adoptadoPor: Int? = null,   // id del usuario
    val favoritoDe: Int? = null     // id del usuario
)
