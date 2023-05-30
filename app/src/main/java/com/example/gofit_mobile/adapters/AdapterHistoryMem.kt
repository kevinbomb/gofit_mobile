package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvHistoryMemBinding
import com.example.gofit_mobile.model.PresensiKelas

class AdapterHistoryMem (private var historyMemberList: MutableList<PresensiKelas>, private val callback: AdapterHistoryMem.onItemCallback) : RecyclerView.Adapter<AdapterHistoryMem.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterHistoryMem.ViewHolder {
        val view = RvHistoryMemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterHistoryMem.ViewHolder, position: Int) {
        val presensikelas = historyMemberList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvNoPresensiK.text = presensikelas.NO_PRESENSIK
        binding.tvTanggalPresensiK.text = presensikelas.TANGGAL_PRESENSIK
        binding.tvNamaKelas.text = presensikelas.NAMA_KELAS
        binding.tvKehadiran.text = if (presensikelas.KEHADIRAN == 1) {
            "Hadir"
        } else {
            "Tidak Hadir"
        }
        binding.tvWaktu.text = presensikelas.WAKTU_PRESENSIK


    }

    override fun getItemCount(): Int {
        return historyMemberList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiKelas>){
        this.historyMemberList.clear()
        this.historyMemberList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvHistoryMemBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiKelas)
    }

}