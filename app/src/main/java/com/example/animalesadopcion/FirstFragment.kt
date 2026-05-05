package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgAnimales = view.findViewById<ImageView>(R.id.imgAnimales)
        val txtTitulo = view.findViewById<TextView>(R.id.txtTitulo)
        val txtSubtitulo = view.findViewById<TextView>(R.id.txtSubtitulo)
        val flecha = view.findViewById<TextView>(R.id.flecha)

        imgAnimales.translationY = 300f
        txtTitulo.alpha = 0f
        txtSubtitulo.alpha = 0f

        imgAnimales.animate()
            .translationY(0f)
            .setDuration(1200)
            .setStartDelay(300)
            .start()

        txtTitulo.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(1000)
            .start()

        txtSubtitulo.animate()
            .alpha(1f)
            .setDuration(800)
            .setStartDelay(1400)
            .start()

        flecha.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}