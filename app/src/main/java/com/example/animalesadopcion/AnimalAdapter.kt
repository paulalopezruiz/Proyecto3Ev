package com.example.animalesadopcion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalesadopcion.BBDD.Animal


// Convierte cada objeto Animal en tarjeta visual
class AnimalAdapter(
    private var listaAnimales: List<Animal>,

    // Funcion al pulsar animal
    private val onClickAnimal: (Animal) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {


    fun actualizarLista(nuevaLista: List<Animal>) {
        listaAnimales = nuevaLista
        notifyDataSetChanged()
    }

    class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAnimal: ImageView = itemView.findViewById(R.id.imgAnimal)
        val txtNombreAnimal: TextView = itemView.findViewById(R.id.txtNombreAnimal)
        val txtSexoAnimal: TextView = itemView.findViewById(R.id.txtSexoAnimal)
        val txtEdadAnimal: TextView = itemView.findViewById(R.id.txtEdadAnimal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    // Rellena cada tarjeta con los datos del animal correspondiente
    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = listaAnimales[position]

        holder.imgAnimal.setImageResource(animal.imagen)
        holder.txtNombreAnimal.text = animal.tipo
        holder.txtSexoAnimal.text = animal.sexo
        holder.txtEdadAnimal.text = animal.localizacion

        // Al pulsar una tarjeta, avisamos al Fragment y le pasamos el animal
        holder.itemView.setOnClickListener {
            onClickAnimal(animal)
        }
    }

    override fun getItemCount(): Int = listaAnimales.size
}