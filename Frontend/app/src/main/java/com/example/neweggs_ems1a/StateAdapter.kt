package com.example.neweggs_ems1a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(
    private val states: List<State>,
    private val onClick: (State) -> Unit
) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    inner class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.stateName)
        val imageView: ImageView = itemView.findViewById(R.id.stateImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val state = states[position]
        holder.nameTextView.text = state.name
        holder.imageView.setImageResource(state.imageResId)
        holder.itemView.setOnClickListener { onClick(state) }
    }

    override fun getItemCount(): Int = states.size
}