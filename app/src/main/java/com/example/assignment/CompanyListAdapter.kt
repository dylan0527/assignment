package com.example.assignment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class CompanyListAdapter internal constructor(private var company:List<Company>):
    RecyclerView.Adapter<CompanyListAdapter.CompanyViewHolder>() {

    inner class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyNameTextView: TextView = itemView.findViewById(R.id.companyNameTextView)
        val companyJobTextView: TextView = itemView.findViewById(R.id.companyJobTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.company_record, parent, false)
        return CompanyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val current = company[position]
        holder.companyNameTextView.text = current.name
        holder.companyJobTextView.text = current.job
        Log.d("main",current.name)
    }

    override fun getItemCount() = company.size
}