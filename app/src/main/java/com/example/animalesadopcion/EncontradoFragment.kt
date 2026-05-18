package com.example.animalesadopcion

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Lifecycle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EncontradoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EncontradoFragment : Fragment(R.layout.fragment_encontrado) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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