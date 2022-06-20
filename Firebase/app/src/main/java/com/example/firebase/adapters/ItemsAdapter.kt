package com.example.firebase.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.data.WatchedItem
import com.example.firebase.ui.ItemTestFragment

class ItemsAdapter (
    private val items: List<WatchedItem>
) : RecyclerView.Adapter<ItemsAdapter.ItemListHolder>() {
    inner class ItemListHolder(private val view: View): RecyclerView.ViewHolder(view)
    {
        val itemName: TextView = view.findViewById(R.id.ItemNameRowLabel)
        val itemStatus: TextView = view.findViewById(R.id.ItemStatusRowLabel)
        val itemButton: Button = view.findViewById(R.id.OpenItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListHolder {
        val view= LayoutInflater.from(parent.context).
        inflate(R.layout.fragment_item_row,parent,false)
        return ItemListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemListHolder, position: Int) {
        holder.itemName.text = items[position].title
        holder.itemStatus.text = if (items[position].isRead!!) "Przeczytana" else "Nieprzeczytana"

        holder.itemButton.setOnClickListener {
            ItemTestFragment.currentItem = items[position]
            holder.itemView.findNavController().navigate(R.id.action_mainFragment_to_itemTestFragment)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}