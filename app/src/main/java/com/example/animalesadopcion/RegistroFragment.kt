package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.animalesadopcion.BBDD.*
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import kotlinx.coroutines.launch

class RegistroFragment : Fragment(R.layout.fragment_registro) {

    private lateinit var vm: AppVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // Referencias a los campos del formulario
        val edtNombre = view.findViewById<EditText>(R.id.edtNombreRegistro)
        val edtTelefono = view.findViewById<EditText>(R.id.edtEmailRegistro)
        val edtPassword = view.findViewById<EditText>(R.id.edtPasswordRegistro)
        val btnEstoyListo = view.findViewById<Button>(R.id.btnEstoyListo)

        btnEstoyListo.setOnClickListener {

            // Recoger datos escritos por el usuario
            val nombre = edtNombre.text.toString()
            val telefono = edtTelefono.text.toString()
            val password = edtPassword.text.toString()

            if (nombre.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (!telefono.all { it.isDigit() }) {
                Toast.makeText(
                    requireContext(),
                    "El teléfono solo puede contener números",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {

                val usuario = Usuario(
                    nombre = nombre,
                    telefono = telefono,
                    password = password
                )

                vm.registrar(usuario)

                Toast.makeText(
                    requireContext(),
                    "Registro completado",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_RegistroFragment_to_LoginFragment)
            }
        }
    }
}