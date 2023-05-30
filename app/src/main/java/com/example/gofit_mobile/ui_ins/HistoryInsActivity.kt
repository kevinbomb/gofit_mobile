package com.example.gofit_mobile.ui_ins

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterHistoryIns
import com.example.gofit_mobile.adapters.AdapterHistoryMem
import com.example.gofit_mobile.adapters.AdapterPresensiKelas
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.PresensiInsResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.databinding.ActivityHistoryInsBinding
import com.example.gofit_mobile.databinding.ActivityHistoryMemBinding
import com.example.gofit_mobile.model.PresensiInstruktur
import com.example.gofit_mobile.model.PresensiKelas
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryInsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryInsBinding

    private var adapter: AdapterHistoryIns? = null
    private var id_ins = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryInsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id_ins = this.getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getLong("ID_INS",0L)
        setUpRv()
    }


    override fun onStart() {
        super.onStart()

        getData(id_ins)
    }

    private fun getData(id: Long) {
        val client = ApiConfig.getApiService()

        client.getHistoryIns(id).enqueue(object : Callback<PresensiInsResponse> {
            override fun onResponse(
                call: Call<PresensiInsResponse>,
                response: Response<PresensiInsResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val dataHistoriIns = responseBody.data
                        adapter?.setData(dataHistoriIns)
                    }
                }

            }

            override fun onFailure(call: Call<PresensiInsResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    this@HistoryInsActivity,
                    "Gagal Get Data Histori Instruktur",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRv() {
        val rvHistoryIns = findViewById<RecyclerView>(R.id.rv_historyins)
        adapter = AdapterHistoryIns(arrayListOf(), object : AdapterHistoryIns.onItemCallback {
            override fun onClick(item: PresensiInstruktur) {
                TODO("Not yet implemented")
            }


        })
        rvHistoryIns.layoutManager = LinearLayoutManager(this@HistoryInsActivity)
        rvHistoryIns.adapter = adapter
    }

}