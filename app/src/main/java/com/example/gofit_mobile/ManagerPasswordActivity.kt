package com.example.gofit_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.GeneralResponse
import com.example.gofit_mobile.databinding.ActivityManagerPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagerPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.setOnClickListener{
            val username = binding.inputLoginId.editText?.text.toString();
            val new_pw = binding.inputLoginPassword.editText?.text.toString();

            resetPwPeg(username, new_pw)
        }
    }

    fun resetPwPeg(id: String, new_pw: String) {
        val client = ApiConfig.getApiService()

        client.pegGantiPw(id, new_pw).enqueue(object:
            Callback<GeneralResponse> {
            override fun onResponse(
                call: Call<GeneralResponse>,
                response: Response<GeneralResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@ManagerPasswordActivity,"Berhasil ganti password", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ManagerPasswordActivity, ManagerLoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<GeneralResponse>, t: Throwable) {
                Toast.makeText(this@ManagerPasswordActivity,"Gagal ganti password", Toast.LENGTH_SHORT).show()
            }
        })
    }
}