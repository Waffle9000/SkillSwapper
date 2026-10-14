package org.setu.skillswapper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class SwapAdapter(
    private var swaps: List<SkillSwapModel>,
    private val listener: OnSwapListener
) : RecyclerView.Adapter<SwapAdapter.SwapViewHolder>() {

    class SwapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wantedText: TextView = itemView.findViewById(R.id.wantedText)
        val offeredText: TextView = itemView.findViewById(R.id.offeredText)
        val locationText: TextView = itemView.findViewById(R.id.locationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwapViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_swap, parent, false)
        return SwapViewHolder(view)
    }

    override fun onBindViewHolder(holder: SwapViewHolder, position: Int) {
        val swap = swaps[position]

        holder.wantedText.text = "Wants: ${swap.skillWanted}"
        holder.offeredText.text = "Offers: ${swap.skillOffered}"
        holder.locationText.text = "Location: ${swap.location}"

        // Tap the whole card to edit
        holder.itemView.setOnClickListener {
            listener.onSwapClick(swap)
        }

        // Long press the whole card to delete
        holder.itemView.setOnLongClickListener {
            listener.onSwapLongClick(swap)
        }
    }

    override fun getItemCount(): Int = swaps.size

    fun updateSwaps(newSwaps: List<SkillSwapModel>) {
        swaps = newSwaps
        Timber.d("Showing ${swaps.size} swaps")
        notifyDataSetChanged()
    }
}