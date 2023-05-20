package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvPerizinanBinding
import com.example.gofit_mobile.databinding.RvPresensikelasBinding
import com.example.gofit_mobile.model.Perizinan
import com.example.gofit_mobile.model.PresensiKelas

class AdapterPresensiKelas (private var presensiKelasList: MutableList<PresensiKelas>, private val callback: AdapterPresensiKelas.onItemCallback) : RecyclerView.Adapter<AdapterPresensiKelas.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterPresensiKelas.ViewHolder {
        val view = RvPresensikelasBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterPresensiKelas.ViewHolder, position: Int) {
        val presensikelas = presensiKelasList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvNoPresensiK.text = presensikelas.NO_PRESENSIK
        binding.tvTanggalPresensiK.text = presensikelas.TANGGAL_PRESENSIK
        binding.tvNamaKelas.text = presensikelas.NAMA_KELAS
        binding.tvTarif.text = presensikelas.TARIF_PRESENSIK.toString()

        binding.btnDelete.setOnClickListener {

            callback.onClick(presensikelas)
        }


    }

    override fun getItemCount(): Int {
        return presensiKelasList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<PresensiKelas>){
        this.presensiKelasList.clear()
        this.presensiKelasList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RvPresensikelasBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiKelas)
    }

}