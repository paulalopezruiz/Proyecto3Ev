package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DetalleAnimalMioFragment :
    Fragment(R.layout.fragment_detalle_animal_mio) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgAnimal =
            view.findViewById<ImageView>(R.id.imgDetalleAnimalMio)

        val txtNombre =
            view.findViewById<TextView>(R.id.txtNombreAnimalMio)

        val txtUbicacion =
            view.findViewById<TextView>(R.id.txtUbicacionAnimalMio)

        val txtSexo =
            view.findViewById<TextView>(R.id.txtSexoAnimalMio)

        val txtEdad =
            view.findViewById<TextView>(R.id.txtEdadAnimalMio)

        val txtPeso =
            view.findViewById<TextView>(R.id.txtPesoAnimalMio)

        val txtInformacion =
            view.findViewById<TextView>(R.id.txtInformacionAnimalMio)

        val txtEditar =
            view.findViewById<TextView>(R.id.txtEditarAnimalMio)

        imgAnimal.setImageResource(R.drawable.perrito_login)

        txtNombre.text = "Luna"
        txtUbicacion.text = "Madrid"
        txtSexo.text = "Hembra"
        txtEdad.text = "2 años"
        txtPeso.text = "8 kg"

        txtInformacion.text =
            "Animal añadido por mí. Aquí aparecerá la información editable."

        txtEditar.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Editar animal",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}