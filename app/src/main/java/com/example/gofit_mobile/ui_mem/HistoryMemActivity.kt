package com.example.gofit_mobile.ui_mem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterHistoryMem
import com.example.gofit_mobile.adapters.AdapterPresensiKelas
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.databinding.ActivityHistoryMemBinding
import com.example.gofit_mobile.model.PresensiKelas
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryMemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryMemBinding

    private var adapter: AdapterHistoryMem? = null
    private var id_mem:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryMemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id_mem = this.getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE)
            .getString("NO_MEM", null)
        setUpRv()

    }


    override fun onStart() {
        super.onStart()

        getData(id_mem!!)
    }

    private fun getData(id: String) {
        val client = ApiConfig.getApiService()

        client.getPresensiKelas(id).enqueue(object : Callback<PresensiKelasResponse> {
            override fun onResponse(
                call: Call<PresensiKelasResponse>,
                response: Response<PresensiKelasResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val dataPresensiKelas = responseBody.data
                        adapter?.setData(dataPresensiKelas)
                    }
                }

            }

            override fun onFailure(call: Call<PresensiKelasResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    this@HistoryMemActivity,
                    "Gagal Get Data Presensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRv() {
        val rvHistoryMem = findViewById<RecyclerView>(R.id.rv_historymem)
        adapter = AdapterHistoryMem(arrayListOf(), object : AdapterHistoryMem.onItemCallback {
            override fun onClick(item: PresensiKelas) {
                TODO("Not yet implemented")
            }


        })
        rvHistoryMem.layoutManager = LinearLayoutManager(this@HistoryMemActivity)
        rvHistoryMem.adapter = adapter
    }

}