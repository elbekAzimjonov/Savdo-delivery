package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.AgentsData

class AgentsAdapter(var list: List<AgentsData>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<AgentsAdapter.AgentViewHolder>() {

    inner class AgentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val adminName = itemView.findViewById<TextView>(R.id.clientsName)!!
        fun onBind(agentsData: AgentsData, position: Int) {
            adminName.text = agentsData.name
            adminName.setOnClickListener {
                onItemClickListener.onItemClick(agentsData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.agents_item, null, false)
        return AgentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    //Filter List with searchView
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<AgentsData>) {
        list = filteredList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(agentsData: AgentsData, position: Int)
    }
}