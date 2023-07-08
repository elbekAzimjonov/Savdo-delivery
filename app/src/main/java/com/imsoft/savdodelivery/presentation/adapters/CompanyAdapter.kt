package com.imsoft.savdodelivery.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.CityDataItem

class CompanyAdapter(
    private val list: ArrayList<CityDataItem>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val companyName = itemView.findViewById<TextView>(R.id.companyName)
        private val cityImage = itemView.findViewById<ImageView>(R.id.cityImage)

        fun onBind(company: CityDataItem, position: Int) {
            companyName.text = company.name
            Glide.with(itemView)
                .load(company.name)
                .transform(CenterCrop())
                .placeholder(R.drawable.ic_group_image)
                .into(cityImage)
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(company, position)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_item, null, false)
        return CompanyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount() = list.size

    interface OnItemClickListener {
        fun onItemClick(company: CityDataItem, position: Int)
    }
}