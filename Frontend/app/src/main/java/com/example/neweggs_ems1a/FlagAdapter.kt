package com.example.neweggs_ems1a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class FlagAdapter(private val flagElements: List<FlagElement>) :
    RecyclerView.Adapter<FlagAdapter.FlagViewHolder>() {

    class FlagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flagImage: ImageView = view.findViewById(R.id.flagElementImage)
        val flagName: TextView = view.findViewById(R.id.flagElementName)
        val flagMeaning: TextView = view.findViewById(R.id.flagElementMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlagViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flag_element, parent, false)
        return FlagViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlagViewHolder, position: Int) {
        val element = flagElements[position]
        holder.flagImage.setImageResource(element.imageResId)
        holder.flagImage.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, element.colorResId)
        )
        holder.flagName.text = element.name
        holder.flagMeaning.text = element.meaning
    }

    override fun getItemCount(): Int = flagElements.size
}