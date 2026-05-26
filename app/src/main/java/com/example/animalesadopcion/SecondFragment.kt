package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ir a pantalla iniciar sesión
        val btnIniciarSesion = view.findViewById<Button>(R.id.btnIniciarSesion)

        // Ir a pantalla  registro
        val btnRegistrarse = view.findViewById<Button>(R.id.btnRegistrarse)

        // Navegación hacia LoginFragment
        btnIniciarSesion.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_LoginFragment)
        }

        btnRegistrarse.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_RegistroFragment)
        }
    }
}