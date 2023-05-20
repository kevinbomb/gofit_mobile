package com.example.gofit_mobile.ui_mem

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
import com.example.gofit_mobile.adapters.AdapterPerizinan
import com.example.gofit_mobile.adapters.AdapterPresensiKelas
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.api.PerizinanResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.model.Perizinan
import com.example.gofit_mobile.model.PresensiKelas
import com.example.gofit_mobile.ui_ins.AddPerizinanActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentPresensiKelas : Fragment() {

    private var adapter: AdapterPresensiKelas? = null
    private var id_mem:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_presensi_kelas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_mem = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE)
            .getString("NO_MEM", null)
        // get data kemudian masukin di recycler view
        setUpRv()
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.fab_add)

        btnAdd.setOnClickListener() {
            val intent = Intent(requireActivity(), AddPresensiKelasActivity::class.java)
            startActivity(intent)
        }
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
                    requireContext(),
                    "Gagal Get Data Presensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setUpRv() {
        val rvPresensiKelas = view?.findViewById<RecyclerView>(R.id.rv_presensikelas)
        adapter = AdapterPresensiKelas(arrayListOf(), object : AdapterPresensiKelas.onItemCallback {
            override fun onClick(item: PresensiKelas) {
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menghapus izin ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus") { _, _ ->
                        deletePresensiKelas(item.NO_PRESENSIK)
                    }
                    .show()


            }

        })
        rvPresensiKelas?.layoutManager = LinearLayoutManager(requireContext())
        rvPresensiKelas?.adapter = adapter
    }

    private fun deletePresensiKelas(id:String){
        val client = ApiConfig.getApiService()

        client.hapusPresensiKelas(id).enqueue(object: Callback<GeneralResponse>{
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    getData(id_mem!!)
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Gagal Membatalkan Data Presensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}