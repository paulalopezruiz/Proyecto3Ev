package com.example.animalesadopcion

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MisAnimalesFragment : Fragment(R.layout.fragment_mis_animales) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val card1 = view.findViewById<LinearLayout>(R.id.cardAnimalMio1)
        val card2 = view.findViewById<LinearLayout>(R.id.cardAnimalMio2)
        val card3 = view.findViewById<LinearLayout>(R.id.cardAnimalMio3)

        card1.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
        }

        card2.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
        }

        card3.setOnClickListener {
            findNavController().navigate(R.id.action_MisAnimalesFragment_to_DetalleAnimalMioFragment)
        }
    }
}