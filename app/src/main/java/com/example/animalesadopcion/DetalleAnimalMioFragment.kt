package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import kotlinx.coroutines.launch
import android.text.InputType

class DetalleAnimalMioFragment :
    Fragment(R.layout.fragment_detalle_animal_mio) {

    private lateinit var vm: AppVM
    private var animalId = 0
    private var editMode = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        animalId = arguments?.getInt("id") ?: 0

        val imgAnimal = view.findViewById<ImageView>(R.id.imgDetalleAnimalMio)
        val txtNombre = view.findViewById<TextView>(R.id.txtNombreAnimalMio)
        val txtUbicacion = view.findViewById<TextView>(R.id.txtUbicacionAnimalMio)
        val txtSexo = view.findViewById<TextView>(R.id.txtSexoAnimalMio)
        val txtEdad = view.findViewById<TextView>(R.id.txtEdadAnimalMio)
        val txtInformacion = view.findViewById<TextView>(R.id.txtInformacionAnimalMio)
        val txtEditar = view.findViewById<TextView>(R.id.txtEditarAnimalMio)

        // Guardamos el fondo original del cuadro de información
        val fondoInfoOriginal = txtInformacion.background

        vm.obtenerAnimal(animalId).observe(viewLifecycleOwner) { animal ->

            imgAnimal.setImageResource(animal.imagen)
            txtNombre.text = animal.tipo
            txtUbicacion.text = animal.localizacion
            txtSexo.text = animal.sexo
            txtEdad.text = animal.estado
            txtInformacion.text = "Información del animal"

            txtEditar.setOnClickListener {

                if (!editMode) {
                    // ACTIVAR MODO EDICIÓN
                    editMode = true
                    txtEditar.text = "✔"

                    activarEdicion(txtNombre)
                    activarEdicion(txtUbicacion)
                    activarEdicion(txtSexo)
                    activarEdicion(txtEdad)

                    // 🔥 Activar edición en información
                    activarEdicion(txtInformacion)
                    txtInformacion.background = null  // quitar fondo decorativo

                } else {
                    // GUARDAR CAMBIOS
                    editMode = false
                    txtEditar.text = "✎"

                    // Restaurar fondo decorativo
                    txtInformacion.background = fondoInfoOriginal

                    val actualizado = animal.copy(
                        tipo = txtNombre.text.toString(),
                        localizacion = txtUbicacion.text.toString(),
                        sexo = txtSexo.text.toString(),
                        estado = txtEdad.text.toString()
                        // Información NO se guarda porque no existe en la BD
                    )

                    viewLifecycleOwner.lifecycleScope.launch {
                        vm.actualizar(actualizado)
                        Toast.makeText(requireContext(), "Cambios guardados", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.MisAnimalesFragment)
                    }
                }
            }
        }
    }

    private fun activarEdicion(textView: TextView) {
        textView.isFocusableInTouchMode = true
        textView.isFocusable = true
        textView.isClickable = true
        textView.isCursorVisible = true
        textView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        textView.setPadding(16, 16, 16, 16)
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
