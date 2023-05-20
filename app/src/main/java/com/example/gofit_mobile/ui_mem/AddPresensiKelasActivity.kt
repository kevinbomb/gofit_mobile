package com.example.gofit_mobile.ui_mem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.CreatePerizinanResponse
import com.example.gofit_mobile.api.CreatePresensiKelasResponse
import com.example.gofit_mobile.databinding.ActivityAddPresensiKelasBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPresensiKelasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPresensiKelasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPresensiKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getString("NO_MEM",null)

        binding.btnSave.setOnClickListener{
            val id_jadwalh = binding.inputLayoutJadwalH.editText?.text.toString()

            createPresensiKelas(id!!, id_jadwalh)
        }

        binding.btnCancle.setOnClickListener(){
            finish()
        }

    }

    fun createPresensiKelas(id:String, jadwalH:String){
        val client = ApiConfig.getApiService()

        client.createPresensiKelas(id, jadwalH).enqueue(object: Callback<CreatePresensiKelasResponse> {
            override fun onResponse(
                call: Call<CreatePresensiKelasResponse>,
                response: Response<CreatePresensiKelasResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@AddPresensiKelasActivity,"Berhasil tambah data", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<CreatePresensiKelasResponse>, t: Throwable) {
                Toast.makeText(this@AddPresensiKelasActivity,"Gagal tambah data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}