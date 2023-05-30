package com.example.gofit_mobile.ui_mem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterHistoryMem
import com.example.gofit_mobile.adapters.AdapterHistoryMemGym
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.PresensiGymResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.databinding.ActivityHistoryMemBinding
import com.example.gofit_mobile.databinding.ActivityHistoryMemGymBinding
import com.example.gofit_mobile.model.PresensiKelas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryMemGymActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryMemGymBinding

    private var adapter: AdapterHistoryMemGym? = null
    private var id_mem:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryMemGymBinding.inflate(layoutInflater)
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

        client.getPresensiGym(id).enqueue(object : Callback<PresensiGymResponse> {
            override fun onResponse(
                call: Call<PresensiGymResponse>,
                response: Response<PresensiGymResponse>
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

            override fun onFailure(call: Call<PresensiGymResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    this@HistoryMemGymActivity,
                    "Gagal Get Data Presensi Gym Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRv() {
        val rvHistoryMem = findViewById<RecyclerView>(R.id.rv_historymemgym)
        adapter = AdapterHistoryMemGym(arrayListOf(), object : AdapterHistoryMemGym.onItemCallback {
            override fun onClick(item: PresensiKelas) {
                TODO("Not yet implemented")
            }


        })
        rvHistoryMem.layoutManager = LinearLayoutManager(this@HistoryMemGymActivity)
        rvHistoryMem.adapter = adapter
    }}

