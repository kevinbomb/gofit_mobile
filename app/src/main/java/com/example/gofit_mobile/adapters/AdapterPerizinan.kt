package com.example.gofit_mobile.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.databinding.RvPerizinanBinding
import com.example.gofit_mobile.model.Perizinan
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterPerizinan (private var perizinanList: MutableList<Perizinan>, private val callback: onItemCallback) : RecyclerView.Adapter<AdapterPerizinan.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvPerizinanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return perizinanList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Perizinan>){
        this.perizinanList.clear()
        this.perizinanList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perizinan = perizinanList[position]
        val context = holder.itemView.context
        val binding = holder.binding

        binding.tvTanggalPerizinan.text = perizinan.TANGGAL_PERIZINAN
        binding.tvKet.text = perizinan.KETERANGAN_PERIZINAN
        binding.tvStatus.text = if (perizinan.STATUS_PERIZINAN == 1) {
            "Dikonfirmasi"
        } else {
            "Belum Dikonfirmasi"
        }

        binding.btnDelete.setOnClickListener {

            callback.onClick(perizinan)
        }


    }

    inner class ViewHolder(val binding: RvPerizinanBinding) : RecyclerView.ViewHolder(binding.root)




    interface onItemCallback{
        fun onClick(item: Perizinan)
    }
}