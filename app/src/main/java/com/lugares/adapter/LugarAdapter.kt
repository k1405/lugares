package com.lugares.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lugares.databinding.LugarFilaBinding
import com.lugares.model.Lugar

class LugarAdapter : RecyclerView.Adapter<LugarAdapter.LugarViewHolder> (){

    inner class LugarViewHolder(private val itemBinding: LugarFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
            fun dibuja(lugar: Lugar){

                itemBinding.tvNombre.text=lugar.nombre
                itemBinding.tvCorreo.text=lugar.correo
                itemBinding.tvTelefono.text=lugar.telefono


            }


    }

    private var listaLugares = emptyList<Lugar>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val itemBinding= LugarFilaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LugarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {

        val lugar = listaLugares[position]
        holder.dibuja(lugar)
    }

    override fun getItemCount(): Int {
      return listaLugares.size
    }

    fun setListaLugares(lugares: List<Lugar>){
        this.listaLugares= lugares
        notifyDataSetChanged()

    }
}