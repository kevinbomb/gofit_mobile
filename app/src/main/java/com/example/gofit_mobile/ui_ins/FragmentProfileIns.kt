package com.example.gofit_mobile.ui_mem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gofit_mobile.api.*
import com.example.gofit_mobile.databinding.FragmentProfileInsBinding
import com.example.gofit_mobile.ui_ins.HistoryInsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentProfileIns : Fragment() {

    private var id_ins = 0L
    private lateinit var binding: FragmentProfileInsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileInsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_ins = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getLong("ID_INS",0L)



        val btnHistory = binding.buttonHistory

        btnHistory.setOnClickListener() {
            val intent = Intent(requireActivity(), HistoryInsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        getData(id_ins.toString())
        getTerlambat(id_ins.toString())
    }

    private fun getData(id: String) {
        val client = ApiConfig.getApiService()
        client.getProfileIns(id).enqueue(object : Callback<ProfileInsResponse> {
            override fun onResponse(
                call: Call<ProfileInsResponse>,
                response: Response<ProfileInsResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val Ins = responseBody.data
                        binding.tvUsername.text = Ins.instruktur.NAMA_INSTRUKTUR
                        binding.tvEmail.text = Ins.instruktur.ALAMAT_INSTRUKTUR
                        binding.tvSaldoValue.text = Ins.instruktur.GAJI_INSTRUKTUR
                        binding.tvDate.text = Ins.ID_INSTRUKTUR
                        binding.tvPhone.text = Ins.instruktur.NO_TELP_INSTRUKTUR
                    }
                }

            }

            override fun onFailure(call: Call<ProfileInsResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Gagal Get Data Member Login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getTerlambat(id: String) {
        val client = ApiConfig.getApiService()
        client.getTerlambat(id).enqueue(object : Callback<TerlambatResponse> {
            override fun onResponse(
                call: Call<TerlambatResponse>,
                response: Response<TerlambatResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val Ins = responseBody.data
                        binding.tvPaketValue.text = Ins
                    }
                }

            }

            override fun onFailure(call: Call<TerlambatResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Gagal Get Data Member Login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}