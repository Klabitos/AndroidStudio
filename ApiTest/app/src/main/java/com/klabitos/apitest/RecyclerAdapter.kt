package com.klabitos.apitest

import android.content.Context
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.klabitos.apitest.databinding.ActivityMainBinding
import com.klabitos.apitest.databinding.ElementoDogBinding
import com.klabitos.apitest.models.DogResponse
import com.squareup.picasso.Picasso

class RecyclerAdapter(private val images: List<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
        //Le metemos por parametros directamente la lista de fotos

    private lateinit var binding: ElementoDogBinding

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.elemento_dog, parent, false))
    }
    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ElementoDogBinding.bind(view)

        fun bind(image:String){
            Picasso.get().load(image).into(binding.ivDog)
        }


    }
}