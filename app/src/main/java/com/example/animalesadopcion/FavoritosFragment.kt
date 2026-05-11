package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritosFragment : Fragment(R.layout.fragment_favoritos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerFavoritos =
            view.findViewById<RecyclerView>(R.id.recyclerFavoritos)

        recyclerFavoritos.layoutManager =
            GridLayoutManager(requireContext(), 2)

        val listaFavoritos = listOf(
            Animal("Perro", "Femenino", "En adopción", "Madrid", R.drawable.perrito_login),
            Animal("Gato", "Masculino", "En adopción", "Valencia", R.drawable.perrito_login),
            Animal("Perro", "Masculino", "Encontrado", "Sevilla", R.drawable.perrito_login),
            Animal("Pájaro", "No conocido", "Perdido", "Madrid", R.drawable.perrito_login)
        )

        recyclerFavoritos.adapter = AnimalAdapter(listaFavoritos)
    }
}