package com.example.gofit_mobile.ui_mo

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterKonfirmasiPresensiKelas
import com.example.gofit_mobile.adapters.AdapterPresensiInstruktur
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.api.PresensiInsResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.model.PresensiInstruktur
import com.example.gofit_mobile.model.PresensiKelas
import com.example.gofit_mobile.ui_mem.AddPresensiGymActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentPresensiInstruktur : Fragment() {

    private var adapter: AdapterPresensiInstruktur? = null
    private var id_mem:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_presensi_instruktur, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        id_mem = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE)
//            .getString("NO_MEM", null)
        // get data kemudian masukin di recycler view
        setUpRv()

        val btnAdd = view.findViewById<FloatingActionButton>(R.id.fab_add)

        btnAdd.setOnClickListener() {
            val intent = Intent(requireActivity(), AddPresensiInsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        getData()
    }

    private fun getData() {
        val client = ApiConfig.getApiService()

        client.getPresensiIns().enqueue(object : Callback<PresensiInsResponse> {
            override fun onResponse(
                call: Call<PresensiInsResponse>,
                response: Response<PresensiInsResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val dataPresensiInstruktur = responseBody.data
                        adapter?.setData(dataPresensiInstruktur)
                    }
                }

            }

            override fun onFailure(call: Call<PresensiInsResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    requireContext(),
                    "Gagal Get Data Presensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRv() {
        val rvPresensiInstruktur = view?.findViewById<RecyclerView>(R.id.rv_presensiinstruktur)
        adapter = AdapterPresensiInstruktur(arrayListOf(), object : AdapterPresensiInstruktur.onItemCallback {
            override fun onClick(item: PresensiInstruktur) {
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi Kelas Selesai")
                    .setMessage("Apakah Instruktur Sudah Selesai?")
                    .setNegativeButton("Belum", null)
                    .setPositiveButton("Sudah") { _, _ ->
                        konfPresensiIns(item.ID_PRESENSII)
                    }
                    .show()


            }

        })
        rvPresensiInstruktur?.layoutManager = LinearLayoutManager(requireContext())
        rvPresensiInstruktur?.adapter = adapter
    }

    private fun konfPresensiIns(id:String){
        val client = ApiConfig.getApiService()

        client.selesai(id).enqueue(object: Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    getData()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Gagal Menyelesaikan Kelas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}