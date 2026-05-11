package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaAnimalesFragment : Fragment(R.layout.fragment_lista_animales) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerAnimales =
            view.findViewById<RecyclerView>(R.id.recyclerAnimales)

        // 🔥 2 COLUMNAS
        recyclerAnimales.layoutManager =
            GridLayoutManager(requireContext(), 2)

        val listaAnimales = listOf(

            Animal(
                "Perro",
                "Hembra",
                "En adopción",
                "Madrid",
                R.drawable.perrito_login
            ),

            Animal(
                "Perro",
                "Macho",
                "Encontrado",
                "Barcelona",
                R.drawable.perrito_login
            ),

            Animal(
                "Gato",
                "Macho",
                "En adopción",
                "Valencia",
                R.drawable.perrito_login
            ),

            Animal(
                "Perro",
                "Hembra",
                "Perdido",
                "Sevilla",
                R.drawable.perrito_login
            ),

            Animal(
                "Pájaro",
                "Hembra",
                "En adopción",
                "Bilbao",
                R.drawable.perrito_login
            ),

            Animal(
                "Gato",
                "Macho",
                "Encontrado",
                "Madrid",
                R.drawable.perrito_login
            )
        )

        val adapter = AnimalAdapter(listaAnimales)

        recyclerAnimales.adapter = adapter
    }
}