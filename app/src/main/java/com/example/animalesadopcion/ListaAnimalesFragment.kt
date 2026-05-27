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

class ListaAnimalesFragment : Fragment(R.layout.fragment_lista_animales) {

    private lateinit var vm: AppVM
    private lateinit var adapter: AnimalAdapter


    private val filtrosActivos = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // RecyclerView donde se muestran los animales disponibles para adoptar
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerAnimales)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)


        adapter = AnimalAdapter(emptyList()) { animal ->
            val bundle = Bundle().apply {
                putInt("id", animal.id)
            }

            findNavController().navigate(
                R.id.action_ListaAnimalesFragment_to_DetalleAnimalFragment,
                bundle
            )
        }

        recycler.adapter = adapter

        // Botones de filtro
        val btnPerro = view.findViewById<View>(R.id.btnPerro)
        val btnGato = view.findViewById<View>(R.id.btnGato)
        val btnPajaro = view.findViewById<View>(R.id.btnPajaro)

        btnPerro.setOnClickListener { toggleFiltro("Perro", btnPerro) }
        btnGato.setOnClickListener { toggleFiltro("Gato", btnGato) }
        btnPajaro.setOnClickListener { toggleFiltro("Pájaro", btnPajaro) }


        vm.animales.observe(viewLifecycleOwner) { lista ->
            val sinAdoptados = lista.filter { it.adoptadoPor == null }
            aplicarFiltros(sinAdoptados)
        }
    }

    // Activa o desactiva un filtro
    private fun toggleFiltro(tipo: String, boton: View) {
        if (filtrosActivos.contains(tipo)) {
            filtrosActivos.remove(tipo)
            boton.alpha = 1f
        } else {
            filtrosActivos.add(tipo)
            boton.alpha = 0.5f
        }

        vm.animales.value?.let { lista ->
            val sinAdoptados = lista.filter { it.adoptadoPor == null }
            aplicarFiltros(sinAdoptados)
        }
    }


    private fun aplicarFiltros(lista: List<Animal>) {
        val filtrada = if (filtrosActivos.isEmpty()) {
            lista
        } else {
            lista.filter { filtrosActivos.contains(it.tipo) }
        }

        adapter.actualizarLista(filtrada)
    }

    // Menu
    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {


            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_general, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val navController = findNavController()

                return when (menuItem.itemId) {

                    // Volver a Home
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