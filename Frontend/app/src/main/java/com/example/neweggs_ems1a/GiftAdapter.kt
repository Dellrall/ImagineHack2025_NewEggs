package com.example.neweggs_ems1a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GiftAdapter(
    private val gifts: List<GiftItem>,
    private val onRedeemClick: (GiftItem) -> Unit
) : RecyclerView.Adapter<GiftAdapter.GiftViewHolder>() {

    class GiftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.giftImageView)
        val nameTextView: TextView = view.findViewById(R.id.giftNameTextView)
        val pointsTextView: TextView = view.findViewById(R.id.giftPointsTextView)
        val redeemButton: Button = view.findViewById(R.id.redeemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiftViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gift, parent, false)
        return GiftViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiftViewHolder, position: Int) {
        val gift = gifts[position]

        holder.imageView.setImageResource(gift.imageRes)
        holder.nameTextView.text = gift.name
        holder.pointsTextView.text = "${gift.pointsRequired} points"

        holder.redeemButton.setOnClickListener {
            onRedeemClick(gift)
        }
    }

    override fun getItemCount() = gifts.size
}