package com.example.gofit_mobile.ui_ins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.CreatePerizinanResponse
import com.example.gofit_mobile.databinding.ActivityAddPerizinanBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPerizinanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPerizinanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddPerizinanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getLong("ID_INS",0L)

        binding.btnSave.setOnClickListener{
            val tgl = binding.inputLayoutTanggal.editText?.text.toString()
            val ket = binding.layoutKeterangan.editText?.text.toString()

            createPerizinan(id, tgl,ket)
        }

        binding.btnCancle.setOnClickListener(){
            finish()
        }
    }

    fun createPerizinan(id:Long, tgl:String, ket:String){
        val client = ApiConfig.getApiService()

        client.createPerizinan(id, tgl, ket).enqueue(object: Callback<CreatePerizinanResponse> {
            override fun onResponse(
                call: Call<CreatePerizinanResponse>,
                response: Response<CreatePerizinanResponse>
            ) {
                if(response.isSuccessful){
                Toast.makeText(this@AddPerizinanActivity,"Berhasil tambah data",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<CreatePerizinanResponse>, t: Throwable) {
                Toast.makeText(this@AddPerizinanActivity,"Gagal tambah data",Toast.LENGTH_SHORT).show()
            }
        })
    }
}