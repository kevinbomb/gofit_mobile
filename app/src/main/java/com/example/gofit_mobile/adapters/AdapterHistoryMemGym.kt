package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvHistoryMemBinding
import com.example.gofit_mobile.databinding.RvHistoryMemGymBinding
import com.example.gofit_mobile.model.PresensiGym
import com.example.gofit_mobile.model.PresensiKelas

class AdapterHistoryMemGym (private var historyMemberList: MutableList<PresensiGym>, private val callback: AdapterHistoryMemGym.onItemCallback) : RecyclerView.Adapter<AdapterHistoryMemGym.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterHistoryMemGym.ViewHolder {
        val view = RvHistoryMemGymBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterHistoryMemGym.ViewHolder, position: Int) {
        val presensigym = historyMemberList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvNoPresensiK.text = presensigym.NO_PRESENSIG
        binding.tvTanggalPresensiK.text = presensigym.TANGGAL_PRESENSIG
        binding.tvSlotWaktu.text = presensigym.SLOT_WAKTU_PRESENSIG
        binding.tvKehadiran.text = if (presensigym.WAKTU_PRESENSIG != null) {
            "Hadir"
        } else {
            "Tidak Hadir"
        }
        binding.tvWaktu.text = presensigym.WAKTU_PRESENSIG


    }

    override fun getItemCount(): Int {
        return historyMemberList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiGym>){
        this.historyMemberList.clear()
        this.historyMemberList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvHistoryMemGymBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiKelas)
    }

}