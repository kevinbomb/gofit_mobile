package com.example.gofit_mobile.model

data class PresensiKelas (
    val NO_PRESENSIK: String,
    val NO_MEMBER: String,
    val ID_JADWALH: String,
    val NAMA_KELAS: String,
    val TANGGAL_PRESENSIK_DIBUAT: String,
    val TANGGAL_PRESENSIK: String,
    val WAKTU_PRESENSIK: String,
    val KEHADIRAN: Int,
    val TARIF_PRESENSIK: Long
)