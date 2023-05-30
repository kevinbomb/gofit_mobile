package com.example.gofit_mobile.ui_mem

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gofit_mobile.HomeActivityMem
import com.example.gofit_mobile.R
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.MemberResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.databinding.FragmentProfileMemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentProfileMem : Fragment() {

    private var id_mem:String? = null
    private lateinit var binding: FragmentProfileMemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileMemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_mem = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE)
            .getString("NO_MEM", null)


        val btnHistory = binding.buttonHistory
        val btnHistoryGym = binding.buttonHistoryGym

        btnHistory.setOnClickListener() {
            val intent = Intent(requireActivity(), HistoryMemActivity::class.java)
            startActivity(intent)
        }
        btnHistoryGym.setOnClickListener() {
            val intent = Intent(requireActivity(), HistoryMemGymActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        getData(id_mem!!)
    }

    private fun getData(id: String) {
        val client = ApiConfig.getApiService()
        client.showMember(id).enqueue(object : Callback<MemberResponse> {
            override fun onResponse(
                call: Call<MemberResponse>,
                response: Response<MemberResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val Member = responseBody.data
                        binding.tvUsername.text = Member.NAMA_MEMBER
                        binding.tvEmail.text = Member.NO_TELP_MEMBER
                        binding.tvExpValue.text = Member.TANGGAL_EXP_MEMBER
                        binding.tvSaldoValue.text = if (Member.SALDO_MEMBER == null) {
                            "0"
                        } else {
                            Member.SALDO_MEMBER.toString()
                        }
                        binding.tvPaketValue.text = if (Member.PAKET_MEMBER == null) {
                            "0"
                        } else {
                            Member.PAKET_MEMBER
                        }
                        binding.tvStatus.text = if (Member.PAKET_MEMBER == "0") {
                            "BELUM AKTIF"
                        } else {
                            "AKTIF"
                        }
                        binding.tvDate.text = Member.NO_MEMBER
                        binding.tvPhone.text = Member.ALAMAT_MEMBER
                    }
                }

            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
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