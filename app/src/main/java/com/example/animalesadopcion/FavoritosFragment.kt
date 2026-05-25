package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalesadopcion.BBDD.Animal

class FavoritosFragment : Fragment(R.layout.fragment_favoritos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerFavoritos =
            view.findViewById<RecyclerView>(R.id.recyclerFavoritos)

        recyclerFavoritos.layoutManager =
            GridLayoutManager(requireContext(), 2)

        val listaFavoritos = listOf(
            Animal(0, "Perro", "Hembra", "En adopción", "Madrid", R.drawable.perrito_login),
            Animal(0, "Gato", "Macho", "En adopción", "Valencia", R.drawable.perrito_login),
            Animal(0, "Perro", "Macho", "Encontrado", "Sevilla", R.drawable.perrito_login),
            Animal(0, "Pájaro", "No conocido", "Perdido", "Madrid", R.drawable.perrito_login)
        )

        recyclerFavoritos.adapter = AnimalAdapter(listaFavoritos) { animal ->

            val bundle = Bundle().apply {
                putString("tipo", animal.tipo)
                putString("sexo", animal.sexo)
                putString("estado", animal.estado)
                putString("localizacion", animal.localizacion)
                putInt("imagen", animal.imagen)
            }

            findNavController().navigate(
                R.id.action_FavoritosFragment_to_DetalleAnimalFragment,
                bundle
            )
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