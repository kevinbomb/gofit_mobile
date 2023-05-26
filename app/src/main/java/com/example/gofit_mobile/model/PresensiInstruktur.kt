package com.example.gofit_mobile.model

data class PresensiInstruktur (
    val ID_PRESENSII: String,
    val ID_INSTRUKTUR: String,
    val instruktur: Instruktur,
    val WAKTU_MULAI: String,
    val WAKTU_SELESAI: String
    )