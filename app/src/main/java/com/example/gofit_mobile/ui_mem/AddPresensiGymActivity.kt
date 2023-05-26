package com.example.gofit_mobile.ui_mem

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.CreatePresensiGymResponse
import com.example.gofit_mobile.databinding.ActivityAddPresensiGymBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddPresensiGymActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPresensiGymBinding
    private lateinit var etSession: AutoCompleteTextView
    private val sessionOptions = arrayOf("7-9", "9-11", "11-13", "13-15", "15-17", "17-19")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPresensiGymBinding.inflate(layoutInflater)
        setContentView(binding.root)

        etSession = binding.etSession
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item_1line, sessionOptions)
        etSession.setAdapter(adapter)

        etSession.setOnItemClickListener { parent, view, position, id ->
            val selectedSession = parent.getItemAtPosition(position) as String
            Toast.makeText(this@AddPresensiGymActivity, "Selected session: $selectedSession", Toast.LENGTH_SHORT).show()
        }

        val id = getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getString("NO_MEM",null)

        binding.btnSave.setOnClickListener{
            val tgl = binding.inputLayoutTanggal.editText?.text.toString()
            val wkt = binding.inputLayoutSession.editText?.text.toString()

            createPresensiGym(id!!, tgl, wkt)
        }
    }


    fun createPresensiGym(id:String, tgl:String, wkt:String){
        val client = ApiConfig.getApiService()

        client.createPresensiGym(id, tgl, wkt).enqueue(object:
            Callback<CreatePresensiGymResponse> {
            override fun onResponse(
                call: Call<CreatePresensiGymResponse>,
                response: Response<CreatePresensiGymResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@AddPresensiGymActivity,"Berhasil tambah data", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this@AddPresensiGymActivity,"GAAGAAL CUI", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<CreatePresensiGymResponse>, t: Throwable) {
                Toast.makeText(this@AddPresensiGymActivity,"Gagal tambah data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}