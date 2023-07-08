package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.ClientsData

class ClientsAdapter(var list: List<ClientsData>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    inner class ClientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val clientsName = itemView.findViewById<TextView>(R.id.clientsName)!!
        fun onBind(clientsData: ClientsData, position: Int) {
            clientsName.text = clientsData.name
            clientsName.setOnClickListener {
                onItemClickListener.onItemClick(clientsData,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.agents_item, null, false)
        return ClientsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    //Filter List with searchView
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<ClientsData>) {
        list = filteredList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(clientsData: ClientsData, position: Int)
    }
}