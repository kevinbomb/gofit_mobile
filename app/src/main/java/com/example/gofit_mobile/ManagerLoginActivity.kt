package com.example.gofit_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.LoginInstrukturResponse
import com.example.gofit_mobile.api.LoginMoResponse
import com.example.gofit_mobile.api.LoginResponse
import com.example.gofit_mobile.databinding.ActivityManagerLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagerLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityManagerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.inputLoginId.editText?.text.toString();
            val password = binding.inputLoginPassword.editText?.text.toString();

//            Toast.makeText(this,"Log in clicked, user: ${username}/${password}",Toast.LENGTH_SHORT).show()
            doLogin(username,password)
        }

        binding.btnMember.setOnClickListener{
            val intent = Intent(this@ManagerLoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnInstruktur.setOnClickListener{
            val intent = Intent(this@ManagerLoginActivity, InstrukturLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnForget.setOnClickListener{
            val intent = Intent(this@ManagerLoginActivity, ManagerPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun doLogin(id: String, password: String) {
        val client = ApiConfig.getApiService()

        client.loginPegawai(id, password).enqueue(object: Callback<LoginMoResponse> {
            override fun onResponse(
                call: Call<LoginMoResponse>,
                response: Response<LoginMoResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Simpan token
                        val pref = getSharedPreferences("TOKEN", MODE_PRIVATE)
                        pref.edit()
                            .putString("TOKEN", responseBody.token)
                            .putString("NAMA", responseBody.user.NAMA_PEGAWAI)
                            .putString("ID_PEG", responseBody.user.ID_PEGAWAI)
                            .apply()

                        if(responseBody.user.ROLE_PEGAWAI == "mo"){
                            val intent = Intent(this@ManagerLoginActivity, HomeActivityIns::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@ManagerLoginActivity, "Login gagal! Pegawai yang bisa login mobile hanya MO!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Response code 401, 500, ...
                    if (response.code() == 401) {
                        Toast.makeText(this@ManagerLoginActivity, "Login gagal! Username atau password salah!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 500) {
                        Toast.makeText(this@ManagerLoginActivity, "Login gagal! Terjadi kesalahan server!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginMoResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}