package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import androidx.core.view.MenuProvider
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

class MisAnimalesFragment : Fragment(R.layout.fragment_mis_animales) {

    private lateinit var vm: AppVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        val main = activity as MainActivity
        val usuarioId = main.vm.usuarioActual.value?.id ?: return

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        val contenedor = view.findViewById<LinearLayout>(R.id.contenedorMisAnimales)

        vm.adoptados(usuarioId).observe(viewLifecycleOwner) { lista ->

            contenedor.removeAllViews()

            val inflater = LayoutInflater.from(requireContext())

            lista.forEach { animal ->

                val card = inflater.inflate(R.layout.card_mis_animales, contenedor, false)

                val txtNombre = card.findViewById<TextView>(R.id.txtNombreAnimalMio)
                txtNombre.text = animal.tipo

                card.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt("id", animal.id)
                    }
                    findNavController().navigate(
                        R.id.action_MisAnimalesFragment_to_DetalleAnimalFragment,
                        bundle
                    )
                }

                contenedor.addView(card)
            }
        }
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
                        androidx.appcompat.app.AlertDialog.Builder(requireContext())
                            .setTitle("Salir")
                            .setMessage("¿Estás seguro de que quieres salir de la aplicación?")
                            .setPositiveButton("Sí") { _, _ -> requireActivity().finish() }
                            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                            .show()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
