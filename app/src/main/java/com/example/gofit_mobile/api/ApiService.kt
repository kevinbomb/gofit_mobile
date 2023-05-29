package com.example.gofit_mobile.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.23/apigofit/public/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService{
    @POST("loginmember")
    @FormUrlEncoded
    fun login (
        @Field("NO_MEMBER") no_member: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("loginpegawai")
    @FormUrlEncoded
    fun loginPegawai (
        @Field("id_pegawai") id_pegawai: String,
        @Field("password") password: String
    ): Call<LoginMoResponse>

    @POST("logininstruktur")
    @FormUrlEncoded
    fun loginInstruktur (
        @Field("ID_INSTRUKTUR") id_instruktur: String,
        @Field("password") password: String
    ): Call<LoginInstrukturResponse>

    @POST("perizinan/{id}")
    @FormUrlEncoded
    fun createPerizinan (
        @Path("id") id: Long,
        @Field("TANGGAL_PERIZINAN") tgl: String,
        @Field("KETERANGAN_PERIZINAN") ket: String
    ): Call<CreatePerizinanResponse>

    @DELETE("perizinan/{id}")
    fun hapusPerizinan(
        @Path("id") id: Long
    ): Call<GeneralResponse>

    @DELETE("presensiKelas/{id}")
    fun hapusPresensiKelas(
        @Path("id") id: Long
    ): Call<GeneralResponse>

    @GET("perizinan/{id}")
    fun getPerizinan(
        @Path("id") id: Long
    ): Call<PerizinanResponse>

    @PUT("instrukturPw")
    @FormUrlEncoded
    fun insGantiPw (
        @Field("ID_INSTRUKTUR") id: String,
        @Field("new_password") new_pw: String
    ): Call<GeneralResponse>

    @PUT("pegawaiPw")
    @FormUrlEncoded
    fun pegGantiPw (
        @Field("id_pegawai") id: String,
        @Field("new_password") new_pw: String
    ): Call<GeneralResponse>

    @GET("presensiKelas/{id}")
    fun getPresensiKelas(
        @Path("id") id: String
    ): Call<PresensiKelasResponse>

    @DELETE("presensiKelas/{id}")
    fun hapusPresensiKelas(
        @Path("id") id: String
    ): Call<GeneralResponse>

    @PUT("konfPresensiKelas/{id}")
    @FormUrlEncoded
    fun konfirmasiPresensiKelas(
        @Path("id") id: String,
        @Field("ID_INSTRUKTUR") tgl: Long
    ): Call<GeneralResponse>

    @PUT("absPresensiKelas/{id}")
    fun absPresensiKelas(
        @Path("id") id: String
    ): Call<GeneralResponse>

    @POST("presensiKelas")
    @FormUrlEncoded
    fun createPresensiKelas (
        @Field("NO_MEMBER") tgl: String,
        @Field("ID_JADWALH") ket: String
    ): Call<CreatePresensiKelasResponse>

    @GET("presensiGym/{id}")
    fun getPresensiGym(
        @Path("id") id: String
    ): Call<PresensiGymResponse>

    @POST("presensiGym")
    @FormUrlEncoded
    fun createPresensiGym (
        @Field("NO_MEMBER") id: String,
        @Field("TANGGAL_PRESENSIG") tgl: String,
        @Field("SLOT_WAKTU_PRESENSIG") wkt: String,
        ): Call<CreatePresensiGymResponse>

    @DELETE("presensiGym/{id}")
    fun hapusPresensiGym(
        @Path("id") id: String
    ): Call<GeneralResponse>

    @GET("presensiKelas")
    fun getPresensiKelasAll(
    ): Call<PresensiKelasResponse>

    @GET("presensiKelasToday")
    fun getPresensiKelasToday(
    ): Call<PresensiKelasResponse>

    @GET("presensiIongoing")
    fun getPresensiIns(
    ): Call<PresensiInsResponse>

    @POST("presensiI")
    @FormUrlEncoded
    fun createPresensiIns (
        @Field("ID_INSTRUKTUR") tgl: String
    ): Call<CreatePresensiInsResponse>

    @PUT("presensiIselesai/{id}")
    fun selesai(
        @Path("id") id: String
    ): Call<GeneralResponse>

    @GET("member/{id}")
    fun showMember(
        @Path("id") id: String
    ): Call<MemberResponse>

    @POST("presensiI1")
    @FormUrlEncoded
    fun getProfileIns (
        @Field("ID_INSTRUKTUR") tgl: String
    ): Call<ProfileInsResponse>

    @GET("presensiIterlambat/{id}")
    fun getTerlambat(
        @Path("id") id: String
    ): Call<TerlambatResponse>
}