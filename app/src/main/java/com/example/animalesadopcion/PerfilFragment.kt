package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMiFamilia = view.findViewById<Button>(R.id.btnMiFamilia)
        val btnFavoritos = view.findViewById<Button>(R.id.btnFavoritosPerfil)

        btnMiFamilia.setOnClickListener {
            Toast.makeText(requireContext(), "Mi Familia", Toast.LENGTH_SHORT).show()
        }

        btnFavoritos.setOnClickListener {
            Toast.makeText(requireContext(), "Favoritos", Toast.LENGTH_SHORT).show()
        }
    }
}