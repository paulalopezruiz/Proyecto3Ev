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
import com.example.animalesadopcion.BBDD.Animal
import com.example.animalesadopcion.BBDD.AppDatabase
import com.example.animalesadopcion.BBDD.Repositorio
import com.example.animalesadopcion.ui.AppVM
import com.example.animalesadopcion.ui.AppVMFactory
import kotlinx.coroutines.launch

class DetalleAnimalFragment : Fragment(R.layout.fragment_detalle_animal) {

    private lateinit var vm: AppVM
    private var animalId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMenu()

        // BD + VM
        val db = AppDatabase.getDatabase(requireContext())
        val repo = Repositorio(db.usuarioDAO(), db.animalDAO())
        vm = AppVMFactory(repo).create(AppVM::class.java)

        // Usuario actual
        val main = activity as MainActivity
        val usuarioId = main.vm.usuarioActual.value?.id

        // ID del animal recibido
        animalId = arguments?.getInt("id") ?: 0

        // Views
        val img = view.findViewById<ImageView>(R.id.imgDetalleAnimal)
        val txtTipo = view.findViewById<TextView>(R.id.txtNombreDetalle)
        val txtLoc = view.findViewById<TextView>(R.id.txtUbicacionDetalle)
        val txtSexo = view.findViewById<TextView>(R.id.txtSexoDetalle)
        val txtEstado = view.findViewById<TextView>(R.id.txtEdadDetalle)
        val txtInfo = view.findViewById<TextView>(R.id.txtInformacionDetalle)
        val txtFav = view.findViewById<TextView>(R.id.txtFavorito)
        val btnAdoptar = view.findViewById<Button>(R.id.btnAdoptar)

        // Observar animal REAL desde Room
        vm.obtenerAnimal(animalId).observe(viewLifecycleOwner) { animal ->

            // Mostrar datos
            img.setImageResource(animal.imagen)
            txtTipo.text = animal.tipo
            txtLoc.text = animal.localizacion
            txtSexo.text = animal.sexo
            txtEstado.text = animal.estado
            txtInfo.text = "${animal.tipo} en estado ${animal.estado}. Ubicado en ${animal.localizacion}."

            // Estado del favorito
            var esFavorito = animal.favoritoDe == usuarioId
            txtFav.text = if (esFavorito) "♥" else "♡"

            // FAVORITO
            txtFav.setOnClickListener {
                if (usuarioId == null) {
                    Toast.makeText(requireContext(), "Inicia sesión", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                esFavorito = !esFavorito
                txtFav.text = if (esFavorito) "♥" else "♡"

                val actualizado = animal.copy(
                    favoritoDe = if (esFavorito) usuarioId else null
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    vm.actualizar(actualizado)
                }
            }

            // ADOPTAR
            btnAdoptar.setOnClickListener {
                if (usuarioId == null) {
                    Toast.makeText(requireContext(), "Inicia sesión", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //Al adoptar → se borra de favoritos automáticamente
                val adoptado = animal.copy(
                    adoptadoPor = usuarioId,
                    favoritoDe = null
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    vm.actualizar(adoptado)
                    Toast.makeText(requireContext(), "¡Has adoptado a ${animal.tipo}!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.ListaAnimalesFragment)
                }
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
                        AlertDialog.Builder(requireContext())
                            .setTitle("Salir")
                            .setMessage("¿Seguro?")
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
