package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvHistoryInsBinding
import com.example.gofit_mobile.model.PresensiInstruktur

class AdapterHistoryIns (private var historyInsList: MutableList<PresensiInstruktur>, private val callback: AdapterHistoryIns.onItemCallback) : RecyclerView.Adapter<AdapterHistoryIns.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterHistoryIns.ViewHolder {
        val view = RvHistoryInsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterHistoryIns.ViewHolder, position: Int) {
        val presensi = historyInsList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvHistoryDate.text = presensi.created_at
        binding.tvStart.text = presensi.WAKTU_MULAI
        binding.tvEnd.text = if (presensi.WAKTU_SELESAI == null) {
            "[!]Kelas Belum Selesai"
        } else {
            presensi.WAKTU_SELESAI
        }
        binding.tvLateValue.text = if (presensi.TERLAMBAT == null) {
            "0"
        } else {
            presensi.TERLAMBAT
        }

    }

    override fun getItemCount(): Int {
        return historyInsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiInstruktur>){
        this.historyInsList.clear()
        this.historyInsList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvHistoryInsBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiInstruktur)
    }

}