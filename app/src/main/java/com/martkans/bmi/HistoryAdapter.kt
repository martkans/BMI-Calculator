package com.martkans.bmi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.martkans.bmi.logic.BmiResult
import java.text.SimpleDateFormat

class HistoryAdapter(private val historyDataset: ArrayList<BmiResult>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.result_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        
        holder.bmiVal.text = historyDataset[position].bmiVal

        if(historyDataset[position].isImperial){
            holder.heightLabel.setText(R.string.bmi_main_height_in)
            holder.massLabel.setText(R.string.bmi_main_mass_lb)
        }

        holder.heightVal.text = historyDataset[position].height
        holder.massVal.text = historyDataset[position].mass

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        holder.date.text = format.format(historyDataset[position].date)
    }

    override fun getItemCount() = historyDataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bmiVal: TextView = itemView.findViewById(R.id.bmiHistoryTV)
        val heightVal: TextView = itemView.findViewById(R.id.heightHistoryTV)
        val massVal: TextView = itemView.findViewById(R.id.massHistoryTV)
        val date: TextView = itemView.findViewById(R.id.dateHistoryTV)

        val heightLabel: TextView = itemView.findViewById(R.id.heightLabelTV)
        val massLabel: TextView = itemView.findViewById(R.id.massLabelTV)
    }
}
