package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RegistroFragment : Fragment(R.layout.fragment_registro) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flecha = view.findViewById<TextView>(R.id.txtFlechaRegistro)
        val edtNombre = view.findViewById<EditText>(R.id.edtNombreRegistro)
        val edtEmail = view.findViewById<EditText>(R.id.edtEmailRegistro)
        val edtPassword = view.findViewById<EditText>(R.id.edtPasswordRegistro)
        val btnEstoyListo = view.findViewById<Button>(R.id.btnEstoyListo)

        flecha.setOnClickListener {
            findNavController().popBackStack()
        }

        btnEstoyListo.setOnClickListener {
            val nombre = edtNombre.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Registro completado", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_RegistroFragment_to_EleccionFragment)            }
        }
    }
}