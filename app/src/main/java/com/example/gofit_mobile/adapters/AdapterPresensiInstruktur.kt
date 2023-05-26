package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvPresensiinstrukturBinding

import com.example.gofit_mobile.model.PresensiInstruktur

class AdapterPresensiInstruktur (private var presensiIList: MutableList<PresensiInstruktur>, private val callback: onItemCallback) : RecyclerView.Adapter<AdapterPresensiInstruktur.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvPresensiinstrukturBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return presensiIList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiInstruktur>){
        this.presensiIList.clear()
        this.presensiIList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val presensiI = presensiIList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvIDinstruktur.text = presensiI.ID_INSTRUKTUR
        binding.tvNamaIns.text = presensiI.instruktur.NAMA_INSTRUKTUR
        binding.tvWaktuMulai.text = presensiI.WAKTU_MULAI
        binding.tvWaktuSelesai.text = if (presensiI.WAKTU_SELESAI == null) {
            "Belum Selesai"
        } else {
            presensiI.WAKTU_SELESAI
        }

        binding.btnDelete.setOnClickListener {

            callback.onClick(presensiI)
        }


    }

    inner class ViewHolder(val binding: RvPresensiinstrukturBinding) : RecyclerView.ViewHolder(binding.root)




    interface onItemCallback{
        fun onClick(item: PresensiInstruktur)
    }
}