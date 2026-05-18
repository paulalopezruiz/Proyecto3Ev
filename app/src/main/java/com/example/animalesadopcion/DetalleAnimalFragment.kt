package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
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


class DetalleAnimalFragment : Fragment(R.layout.fragment_detalle_animal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgDetalleAnimal = view.findViewById<ImageView>(R.id.imgDetalleAnimal)
        val txtNombreDetalle = view.findViewById<TextView>(R.id.txtNombreDetalle)
        val txtUbicacionDetalle = view.findViewById<TextView>(R.id.txtUbicacionDetalle)
        val txtSexoDetalle = view.findViewById<TextView>(R.id.txtSexoDetalle)
        val txtEdadDetalle = view.findViewById<TextView>(R.id.txtEdadDetalle)
        val txtPesoDetalle = view.findViewById<TextView>(R.id.txtPesoDetalle)
        val txtInformacionDetalle = view.findViewById<TextView>(R.id.txtInformacionDetalle)
        val txtFavorito = view.findViewById<TextView>(R.id.txtFavorito)
        val btnAdoptar = view.findViewById<Button>(R.id.btnAdoptar)

        imgDetalleAnimal.setImageResource(R.drawable.perrito_login)

        txtNombreDetalle.text = "Luna"
        txtUbicacionDetalle.text = "Madrid"
        txtSexoDetalle.text = "Hembra"
        txtEdadDetalle.text = "2 años"
        txtPesoDetalle.text = "8 kg"
        txtInformacionDetalle.text = "Luna es una perrita tranquila..."

        var favorito = false

        txtFavorito.setOnClickListener {
            favorito = !favorito
            txtFavorito.text = if (favorito) "♥" else "♡"
        }

        btnAdoptar.setOnClickListener {
            Toast.makeText(requireContext(), "Solicitud enviada", Toast.LENGTH_SHORT).show()
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