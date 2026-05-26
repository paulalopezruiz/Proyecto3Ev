package com.example.animalesadopcion

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.example.animalesadopcion.BBDD.Animal
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class EncontradoFragment : Fragment(R.layout.fragment_encontrado) {

    private lateinit var vm: AppVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // Cada spinner muestra una lista de opcione
        val spinnerTipo = view.findViewById<Spinner>(R.id.spinnerTipo)
        val spinnerSexo = view.findViewById<Spinner>(R.id.spinnerSexo)
        val spinnerEstado = view.findViewById<Spinner>(R.id.spinnerEstado)
        val spinnerLocalizacion = view.findViewById<Spinner>(R.id.spinnerLocalizacion)

        // Guardar el animal encontrado
        val btnGuardar = view.findViewById<Button>(R.id.btnSalvar)

        // Cargar opciones dentro de un Spinner.
        fun cargarSpinner(spinner: Spinner, arrayId: Int) {
            ArrayAdapter.createFromResource(
                requireContext(),
                arrayId,
                android.R.layout.simple_spinner_item
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                spinner.adapter = adapter
            }
        }

        // Cargamos los valores de arrays.xml en cada spinner.
        cargarSpinner(spinnerTipo, R.array.tipos_animales)
        cargarSpinner(spinnerSexo, R.array.sexos_animales)
        cargarSpinner(spinnerEstado, R.array.estados_animales)
        cargarSpinner(spinnerLocalizacion, R.array.localizaciones_animales)

        btnGuardar.setOnClickListener {

            // Recoger opción seleccionada en cada spinner
            val tipo = spinnerTipo.selectedItem.toString()
            val sexo = spinnerSexo.selectedItem.toString()
            val estado = spinnerEstado.selectedItem.toString()
            val localizacion = spinnerLocalizacion.selectedItem.toString()


            if (tipo.isEmpty() || sexo.isEmpty() || estado.isEmpty() || localizacion.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Rellena todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Elegir imagen según el tipo de animal seleccionado.
            val imagen = when (tipo) {
                "Perro" -> R.drawable.perro
                "Gato" -> R.drawable.gato
                "Conejo" -> R.drawable.conejo
                "Pájaro" -> R.drawable.pajaro

                else -> R.drawable.pajaro
            }

            val animal = Animal(
                tipo = tipo,
                sexo = sexo,
                estado = estado,
                localizacion = localizacion,
                imagen = imagen
            )

            viewLifecycleOwner.lifecycleScope.launch {
                vm.insertar(animal)

                Toast.makeText(
                    requireContext(),
                    "Animal añadido correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.EleccionFragment)
            }
        }
    }

    // Menú superior de la toolbar.
    // Si la profesora pide añadir una nueva opción al menú,
    // hay que añadirla en el XML del menú y después gestionarla aquí.
    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            // Cargamos el menú general: Home, Perfil y Salir
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_general, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val navController = findNavController()

                return when (menuItem.itemId) {

                    // Volver a la pantalla principal
                    R.id.menu_home -> {
                        navController.navigate(R.id.EleccionFragment)
                        true
                    }

                    // Ir al perfil del usuario
                    R.id.menu_profile -> {
                        navController.navigate(R.id.PerfilFragment)
                        true
                    }

                    // Salir de la aplicación mostrando un diálogo
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

                    // Ejemplo para examen:
                    // Si en menu_general.xml añades:
                    //
                    // <item
                    //     android:id="@+id/menu_hola"
                    //     android:title="Hola"
                    //     app:showAsAction="never" />
                    //
                    // Aquí lo controlarías así:
                    //
                    // R.id.menu_hola -> {
                    //     Toast.makeText(requireContext(), "Hola", Toast.LENGTH_SHORT).show()
                    //     true
                    // }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}