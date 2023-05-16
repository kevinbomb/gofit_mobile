package com.example.gofit_mobile.model

data class Perizinan (
    val ID_PERIZINAN: Long,
    val ID_INSTRUKTUR: Long,
    val KETERANGAN_PERIZINAN: String,
    val STATUS_PERIZINAN: Int,
    val TANGGAL_PERIZINAN: String,
    val TANGGAL_PERIZINAN_DIAJUKAN: String
)