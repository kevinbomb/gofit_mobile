package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.databinding.RvKonfirmasipresensikelasBinding
import com.example.gofit_mobile.databinding.RvPresensikelasBinding
import com.example.gofit_mobile.model.PresensiKelas

class AdapterKonfirmasiPresensiKelas(private var presensiKelasList: MutableList<PresensiKelas>, private val callback: AdapterKonfirmasiPresensiKelas.onItemCallback) : RecyclerView.Adapter<AdapterKonfirmasiPresensiKelas.ViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterKonfirmasiPresensiKelas.ViewHolder {
        val view = RvKonfirmasipresensikelasBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterKonfirmasiPresensiKelas.ViewHolder, position: Int) {
        val presensikelas = presensiKelasList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvNoPresensiK.text = presensikelas.NO_PRESENSIK
        binding.tvTanggalPresensiK.text = presensikelas.TANGGAL_PRESENSIK
        binding.tvNamaKelas.text = presensikelas.NAMA_KELAS
        binding.tvNoMember.text = presensikelas.NO_MEMBER

        binding.btnCheck.setOnClickListener {

            callback.onClick(presensikelas)
        }

//        binding.btnCheck.setOnClickListener {
//
//        }


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

    inner class ViewHolder(val binding: RvKonfirmasipresensikelasBinding) : RecyclerView.ViewHolder(binding.root)

    interface onItemCallback{
        fun onClick(item: PresensiKelas)
    }
}