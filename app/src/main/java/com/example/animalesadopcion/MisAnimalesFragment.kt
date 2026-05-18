package com.example.animalesadopcion

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider

class MisAnimalesFragment : Fragment(R.layout.fragment_mis_animales) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card1 = view.findViewById<LinearLayout>(R.id.cardAnimalMio1)
        val card2 = view.findViewById<LinearLayout>(R.id.cardAnimalMio2)
        val card3 = view.findViewById<LinearLayout>(R.id.cardAnimalMio3)

        card1.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
        }

        card2.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
        }

        card3.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
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