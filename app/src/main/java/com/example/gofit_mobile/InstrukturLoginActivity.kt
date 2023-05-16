package com.example.gofit_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gofit_mobile.api.ApiConfig
import com.example.gofit_mobile.api.LoginInstrukturResponse
import com.example.gofit_mobile.databinding.ActivityInstrukturLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstrukturLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInstrukturLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityInstrukturLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.inputLoginId.editText?.text.toString();
            val password = binding.inputLoginPassword.editText?.text.toString();

//            Toast.makeText(this,"Log in clicked, user: ${username}/${password}",Toast.LENGTH_SHORT).show()
            doLogin(username,password)
        }

        binding.btnMember.setOnClickListener{
            val intent = Intent(this@InstrukturLoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnManager.setOnClickListener{
            val intent = Intent(this@InstrukturLoginActivity, ManagerLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnForget.setOnClickListener{
            val intent = Intent(this@InstrukturLoginActivity, InstrukturPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun doLogin(id_instruktur: String, password: String) {
        val client = ApiConfig.getApiService()

        client.loginInstruktur(id_instruktur, password).enqueue(object: Callback<LoginInstrukturResponse> {
            override fun onResponse(call: Call<LoginInstrukturResponse>, response: Response<LoginInstrukturResponse>) {
                // Kalau ada response (entah itu success atau gagal)
                // Mis. error 401, 500, dsb... masih masuk sini

                if (response.isSuccessful) {
                    // Response code bukan 401, 500, ...
                    val responseBody = response.body()
                    if (responseBody != null) {
//                        Toast.makeText(this@MainActivity, "Login berhasil! Token: ${responseBody.message}", Toast.LENGTH_SHORT).show()

                        // Simpan token
                        val pref = getSharedPreferences("TOKEN", MODE_PRIVATE)
                        pref.edit()
                            .putString("TOKEN", responseBody.token)
                            .putString("NAMA", responseBody.data.NAMA_INSTRUKTUR)
                            .putLong("ID_INS", responseBody.data.ID_INSTRUKTUR)
                            .apply()

                        // Pindah ke halaman home
                        val intent = Intent(this@InstrukturLoginActivity, HomeActivityIns::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // Response code 401, 500, ...
                    if (response.code() == 401) {
                        Toast.makeText(this@InstrukturLoginActivity, "Login gagal! Username atau password salah!", Toast.LENGTH_SHORT).show()
                    } else if (response.code() == 500) {
                        Toast.makeText(this@InstrukturLoginActivity, "Login gagal! Terjadi kesalahan server!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginInstrukturResponse>, t: Throwable) {
                // Kalau tidak ada response (mis. internet error)
            }

        })
    }
}