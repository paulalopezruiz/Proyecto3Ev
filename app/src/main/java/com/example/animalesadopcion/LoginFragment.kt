package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var vm: AppVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // Referencias a los campos del XML
        val edtNombre = view.findViewById<EditText>(R.id.edtNombreLogin)
        val edtPassword = view.findViewById<EditText>(R.id.edtPasswordLogin)
        val btnEntrar = view.findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {

            // Recogemos lo que ha escrito el usuario
            val nombre = edtNombre.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (nombre.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {

                val usuario = vm.login(nombre, password)

                if (usuario != null) {


                    val main = activity as MainActivity
                    main.vm.setUsuarioActual(usuario)

                    Toast.makeText(
                        requireContext(),
                        "Bienvenido ${usuario.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_LoginFragment_to_EleccionFragment)

                } else {

                    Toast.makeText(
                        requireContext(),
                        "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}