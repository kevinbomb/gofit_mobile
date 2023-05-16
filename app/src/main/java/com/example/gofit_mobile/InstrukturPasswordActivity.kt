package com.example.gofit_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.api.LoginInstrukturResponse
import com.example.gofit_mobile.databinding.ActivityInstrukturPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstrukturPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstrukturPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInstrukturPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener{
            val username = binding.inputLoginId.editText?.text.toString();
            val new_pw = binding.inputLoginPassword.editText?.text.toString();

            resetPwIns(username, new_pw)
        }
    }

    fun resetPwIns(id_instruktur: String, new_pw: String) {
        val client = ApiConfig.getApiService()

        client.insGantiPw(id_instruktur, new_pw).enqueue(object:
            Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@InstrukturPasswordActivity,"Berhasil ganti password",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@InstrukturPasswordActivity, InstrukturLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Toast.makeText(this@InstrukturPasswordActivity,"Gagal ganti password",Toast.LENGTH_SHORT).show()
            }
        })
    }
}