package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DetalleAnimalFragment : Fragment(R.layout.fragment_detalle_animal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgDetalleAnimal = view.findViewById<ImageView>(R.id.imgDetalleAnimal)
        val txtNombreDetalle = view.findViewById<TextView>(R.id.txtNombreDetalle)
        val txtUbicacionDetalle = view.findViewById<TextView>(R.id.txtUbicacionDetalle)
        val txtSexoDetalle = view.findViewById<TextView>(R.id.txtSexoDetalle)
        val txtEdadDetalle = view.findViewById<TextView>(R.id.txtEdadDetalle)
        val txtPesoDetalle = view.findViewById<TextView>(R.id.txtPesoDetalle)
        val txtInformacionDetalle = view.findViewById<TextView>(R.id.txtInformacionDetalle)
        val txtFavorito = view.findViewById<TextView>(R.id.txtFavorito)
        val btnAdoptar = view.findViewById<Button>(R.id.btnAdoptar)

        imgDetalleAnimal.setImageResource(R.drawable.perrito_login)

        txtNombreDetalle.text = "Luna"
        txtUbicacionDetalle.text = "Madrid"
        txtSexoDetalle.text = "Hembra"
        txtEdadDetalle.text = "2 años"
        txtPesoDetalle.text = "8 kg"
        txtInformacionDetalle.text = "Luna es una perrita tranquila, cariñosa y busca una nueva familia."

        var favorito = false

        txtFavorito.setOnClickListener {
            favorito = !favorito

            if (favorito) {
                txtFavorito.text = "♥"
            } else {
                txtFavorito.text = "♡"
            }
        }

        btnAdoptar.setOnClickListener {
            Toast.makeText(requireContext(), "Solicitud de adopción enviada", Toast.LENGTH_SHORT).show()
        }
    }
}