package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EleccionFragment : Fragment(R.layout.fragment_eleccion) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtEncontrado = view.findViewById<TextView>(R.id.txtEncontrado)
        val btnQuieroAdoptar = view.findViewById<TextView>(R.id.btnQuieroAdoptar)

        txtEncontrado.setOnClickListener {
            findNavController().navigate(R.id.action_EleccionFragment_to_EncontradoFragment)
        }

        btnQuieroAdoptar.setOnClickListener {
            findNavController().navigate(R.id.action_EleccionFragment_to_ListaAnimalesFragment)
        }
    }
}