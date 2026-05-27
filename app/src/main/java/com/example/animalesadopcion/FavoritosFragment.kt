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
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory

class FavoritosFragment : Fragment(R.layout.fragment_favoritos) {

    private lateinit var vm: AppVM
    private lateinit var adapter: AnimalAdapter

    private val filtrosActivos = mutableSetOf<String>()
    private var listaFavoritos = listOf<Animal>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val main = activity as MainActivity
        val usuarioId = main.vm.usuarioActual.value?.id ?: return

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerFavoritos)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = AnimalAdapter(emptyList()) { animal ->
            val bundle = Bundle().apply {
                putInt("id", animal.id)
            }

            findNavController().navigate(
                R.id.action_FavoritosFragment_to_DetalleAnimalFragment,
                bundle
            )
        }

        recycler.adapter = adapter

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // Botones de filtro
        val btnPerro = view.findViewById<View>(R.id.btnPerro)
        val btnGato = view.findViewById<View>(R.id.btnGato)
        val btnPajaro = view.findViewById<View>(R.id.btnPajaro)

        btnPerro.setOnClickListener {
            toggleFiltro("Perro", btnPerro)
        }

        btnGato.setOnClickListener {
            toggleFiltro("Gato", btnGato)
        }

        btnPajaro.setOnClickListener {
            toggleFiltro("Pájaro", btnPajaro)
        }

        vm.favoritos(usuarioId).observe(viewLifecycleOwner) { lista ->
            listaFavoritos = lista
            aplicarFiltros()
        }

        addMenu()
    }

    private fun toggleFiltro(tipo: String, boton: View) {
        if (filtrosActivos.contains(tipo)) {
            filtrosActivos.remove(tipo)
            boton.alpha = 1f
        } else {
            filtrosActivos.add(tipo)
            boton.alpha = 0.5f
        }

        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val listaFiltrada = if (filtrosActivos.isEmpty()) {
            listaFavoritos
        } else {
            listaFavoritos.filter { animal ->
                filtrosActivos.contains(animal.tipo)
            }
        }

        adapter.actualizarLista(listaFiltrada)
    }

    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            // Cargamos el xml del menú (home, perfil y salir)
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

                    // Ir al perfil
                    R.id.menu_profile -> {
                        navController.navigate(R.id.PerfilFragment)
                        true
                    }

                    // Salir
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