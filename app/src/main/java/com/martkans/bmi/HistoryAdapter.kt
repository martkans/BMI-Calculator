package com.martkans.bmi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HistoryAdapter(private val historyDataset: ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.result_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.test.text = historyDataset[position]
    }

    override fun getItemCount() = historyDataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val test: TextView = itemView.findViewById(R.id.bmiLabelTV)
    }
}
