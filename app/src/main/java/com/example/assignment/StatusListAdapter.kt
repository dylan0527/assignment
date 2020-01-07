package com.example.assignment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatusListAdapter internal constructor(context:Context):
    RecyclerView.Adapter<StatusListAdapter.StatusViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var status = emptyList<Status>()

    inner class StatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyNameTextView: TextView = itemView.findViewById(R.id.companyNameStatus)
        val companyJobTextView: TextView = itemView.findViewById(R.id.companyJobStatus)
        val status:TextView = itemView.findViewById(R.id.companyJobStatus)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val itemView = inflater.inflate(R.layout.status_record, parent, false)
        return StatusViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val current = status[position]
        holder.companyNameTextView.text = current.name
        holder.companyJobTextView.text = current.job
        holder.status.text=current.status
    }

    internal fun setStatus(status: List<Status>) {
        this.status = status
        notifyDataSetChanged()
    }

    override fun getItemCount() = status.size
}