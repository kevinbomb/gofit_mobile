import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit_mobile.R
import com.example.gofit_mobile.adapters.AdapterKonfirmasiPresensiKelas
import com.example.gofit_mobile.adapters.AdapterPresensiKelas
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.api.PresensiKelasResponse
import com.example.gofit_mobile.model.PresensiKelas
import com.example.gofit_mobile.ui_mem.AddPresensiKelasActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentKonfirmasiPresensiKelas : Fragment() {

    private var adapter: AdapterKonfirmasiPresensiKelas? = null
    private var id_ins = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_konfirmasi_presensi_kelas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id_ins = requireContext().getSharedPreferences("TOKEN", AppCompatActivity.MODE_PRIVATE).getLong("ID_INS",0L)
        // get data kemudian masukin di recycler view
        setUpRv()

    }

    override fun onStart() {
        super.onStart()

        getData()
    }

    private fun getData() {
        val client = ApiConfig.getApiService()

        client.getPresensiKelasToday().enqueue(object : Callback<PresensiKelasResponse> {
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
        adapter = AdapterKonfirmasiPresensiKelas(arrayListOf(), object : AdapterKonfirmasiPresensiKelas.onItemCallback {
            override fun onClick(item: PresensiKelas) {
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi Kehadiran")
                    .setMessage("Apakah member yang ini hadir?")
                    .setNegativeButton("Tidak") { _, _ ->
                        absPresensiKelas(item.NO_PRESENSIK)
                    }
                    .setPositiveButton("Ya") { _, _ ->
                        konfPresensiKelas(item.NO_PRESENSIK, id_ins)
                    }
                    .show()


            }

        })
        rvPresensiKelas?.layoutManager = LinearLayoutManager(requireContext())
        rvPresensiKelas?.adapter = adapter
    }

    private fun konfPresensiKelas(id:String, ins:Long){
        val client = ApiConfig.getApiService()

        client.konfirmasiPresensiKelas(id, ins).enqueue(object: Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if (response.isSuccessful) {
                    getData()
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        "Instruktur belum dipresensi MO!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Gagal Melakukan Presensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun absPresensiKelas(id:String){
        val client = ApiConfig.getApiService()

        client.absPresensiKelas(id).enqueue(object: Callback<GeneralResponse> {
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
                    "Gagal Melakukan Absensi Kelas Member",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}