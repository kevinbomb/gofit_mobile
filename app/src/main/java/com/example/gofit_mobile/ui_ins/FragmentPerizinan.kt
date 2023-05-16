package com.example.gofit_mobile.ui_ins

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.InstrukturLoginActivity
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterPerizinan
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.api.LoginInstrukturResponse
import com.example.gofit_mobile.api.PerizinanResponse
import com.example.gofit_mobile.model.Perizinan
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets

class FragmentPerizinan : Fragment() {

    private var adapter: AdapterPerizinan? = null
    private var id_ins = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perizinan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_ins = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getLong("ID_INS",0L)
        // get data kemudian masukin di recycler view
        setUpRv()
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.fab_add)

        btnAdd.setOnClickListener(){
            val intent = Intent(requireActivity(), AddPerizinanActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        getData(id_ins)
    }

    private fun getData(id: Long){
        val client = ApiConfig.getApiService()

        client.getPerizinan(id).enqueue(object: Callback<PerizinanResponse> {
            override fun onResponse(
                call: Call<PerizinanResponse>,
                response: Response<PerizinanResponse>
            ) {
                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
                        //tampil data
                        val dataPerizinan = responseBody.data
                        adapter?.setData(dataPerizinan)
                    }
                }

            }

            override fun onFailure(call: Call<PerizinanResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Gagal Get Data Perizinan Member", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpRv(){
        val rvPerizinan = view?.findViewById<RecyclerView>(R.id.rv_perizinan)
        adapter = AdapterPerizinan(arrayListOf(), object : AdapterPerizinan.onItemCallback{
            override fun onClick(item: Perizinan) {
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menghapus izin ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus"){_,_ ->
                        deletePerizinan(item.ID_PERIZINAN)
                    }
                    .show()


            }

        })
        rvPerizinan?.layoutManager = LinearLayoutManager(requireContext())
        rvPerizinan?.adapter = adapter
    }

    private fun deletePerizinan(id:Long){
        val client = ApiConfig.getApiService()

        client.hapusPerizinan(id).enqueue(object: Callback<GeneralResponse>{
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                getData(id_ins)
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}