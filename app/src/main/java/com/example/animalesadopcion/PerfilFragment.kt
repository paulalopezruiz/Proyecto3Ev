package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnMiFamilia = view.findViewById<Button>(R.id.btnMiFamilia)
        val btnFavoritos = view.findViewById<Button>(R.id.btnFavoritosPerfil)

        btnMiFamilia.setOnClickListener {
            findNavController().navigate(R.id.MisAnimalesFragment)
        }

        btnFavoritos.setOnClickListener {
            findNavController().navigate(R.id.FavoritosFragment)
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profile, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                val navController = findNavController()

                return when (menuItem.itemId) {

                    R.id.menu_home -> {
                        navController.navigate(R.id.EleccionFragment)
                        true
                    }

                    R.id.menu_logout -> {

                        AlertDialog.Builder(requireContext())
                            .setTitle("Salir")
                            .setMessage("¿Estás seguro de que quieres salir de la aplicación?")
                            .setPositiveButton("Sí") { _, _ ->
                                requireActivity().finish()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()

                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}