package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvPresensigymBinding
import com.example.gofit_mobile.model.PresensiGym

class AdapterPresensiGym (private var presensiGymList: MutableList<PresensiGym>, private val callback: AdapterPresensiGym.onItemCallback) : RecyclerView.Adapter<AdapterPresensiGym.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterPresensiGym.ViewHolder{
        val view = RvPresensigymBinding .inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterPresensiGym.ViewHolder, position: Int) {
        val presensigym = presensiGymList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvNoPresensiG.text = presensigym.NO_PRESENSIG
        binding.tvTanggalPresensiG.text = presensigym.TANGGAL_PRESENSIG
        binding.tvSlotWaktu.text = presensigym.SLOT_WAKTU_PRESENSIG

        binding.btnDelete.setOnClickListener {

            callback.onClick(presensigym)
        }


    }

    override fun getItemCount(): Int {
        return presensiGymList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiGym>){
        this.presensiGymList.clear()
        this.presensiGymList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvPresensigymBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiGym)
    }

}