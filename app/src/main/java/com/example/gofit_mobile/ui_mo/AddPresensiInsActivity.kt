package com.example.gofit_mobile.ui_mo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.CreatePresensiInsResponse
import com.example.gofit_mobile.api.CreatePresensiKelasResponse
import com.example.gofit_mobile.databinding.ActivityAddPresensiInsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPresensiInsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPresensiInsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPresensiInsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener{
            val id_ins = binding.inputLayoutIdIns.editText?.text.toString()

            createPresensiIns(id_ins)
        }

        binding.btnCancle.setOnClickListener(){
            finish()
        }
    }

    fun createPresensiIns(id_ins:String){
        val client = ApiConfig.getApiService()

        client.createPresensiIns(id_ins).enqueue(object:
            Callback<CreatePresensiInsResponse> {
            override fun onResponse(
                call: Call<CreatePresensiInsResponse>,
                response: Response<CreatePresensiInsResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@AddPresensiInsActivity,"Berhasil tambah data", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<CreatePresensiInsResponse>, t: Throwable) {
                Toast.makeText(this@AddPresensiInsActivity,"Gagal tambah data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}