package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgAnimales = view.findViewById<ImageView>(R.id.imgAnimales)
        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        val txtSubtitulo = view.findViewById<TextView>(R.id.txtSubtitulo)

        // Posición inicial
        imgAnimales.translationY = 300f
        txtTitulo.alpha = 0f
        txtSubtitulo.alpha = 0f

        // Animación del logo (sube)
        imgAnimales.animate()
            .translationY(0f)
            .setDuration(1200)
            .setStartDelay(300)
            .start()

        // Aparece el título
        txtTitulo.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(1000)
            .start()

        // Aparece el subtítulo
        txtSubtitulo.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(1400)
            .start()
    }
}