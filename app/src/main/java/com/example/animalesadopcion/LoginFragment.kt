package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val edtNombre = view.findViewById<EditText>(R.id.edtNombreLogin)
        val edtPassword = view.findViewById<EditText>(R.id.edtPasswordLogin)
        val btnEntrar = view.findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val nombre = edtNombre.text.toString()
            val password = edtPassword.text.toString()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.action_LoginFragment_to_EleccionFragment)
            }
        }
    }
}