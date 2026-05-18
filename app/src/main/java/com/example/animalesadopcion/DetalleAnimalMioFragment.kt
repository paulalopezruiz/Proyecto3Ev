package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController

class DetalleAnimalMioFragment :
    Fragment(R.layout.fragment_detalle_animal_mio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgAnimal = view.findViewById<ImageView>(R.id.imgDetalleAnimalMio)
        val txtNombre = view.findViewById<TextView>(R.id.txtNombreAnimalMio)
        val txtUbicacion = view.findViewById<TextView>(R.id.txtUbicacionAnimalMio)
        val txtSexo = view.findViewById<TextView>(R.id.txtSexoAnimalMio)
        val txtEdad = view.findViewById<TextView>(R.id.txtEdadAnimalMio)
        val txtPeso = view.findViewById<TextView>(R.id.txtPesoAnimalMio)
        val txtInformacion = view.findViewById<TextView>(R.id.txtInformacionAnimalMio)
        val txtEditar = view.findViewById<TextView>(R.id.txtEditarAnimalMio)

        imgAnimal.setImageResource(R.drawable.perrito_login)

        txtNombre.text = "Luna"
        txtUbicacion.text = "Madrid"
        txtSexo.text = "Hembra"
        txtEdad.text = "2 años"
        txtPeso.text = "8 kg"

        txtInformacion.text = "Animal añadido por mí..."

        txtEditar.setOnClickListener {
            Toast.makeText(requireContext(), "Editar animal", Toast.LENGTH_SHORT).show()
        }

        addMenu()
    }

    private fun addMenu() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_general, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                val navController = findNavController()

                return when (menuItem.itemId) {

                    R.id.menu_home -> {
                        navController.navigate(R.id.EleccionFragment)
                        true
                    }

                    R.id.menu_profile -> {
                        navController.navigate(R.id.PerfilFragment)
                        true
                    }

                    R.id.menu_logout -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Salir")
                            .setMessage("¿Estás seguro de que quieres salir de la aplicación?")
                            .setPositiveButton("Sí") { _, _ -> requireActivity().finish() }
                            .setNegativeButton("No") { d, _ -> d.dismiss() }
                            .show()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}