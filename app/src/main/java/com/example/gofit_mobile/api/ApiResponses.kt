package com.example.gofit_mobile.api

import com.example.gofit_mobile.model.Instruktur
import com.example.gofit_mobile.model.Member
import com.example.gofit_mobile.model.Perizinan

data class LoginResponse(
    val message: String,
    val data: Member,
    val token: String,
    val kode: String
)

data class LoginInstrukturResponse(
    val message: String,
    val data: Instruktur,
    val token: String,
    val kode: String
)

data class GeneralResponse(
    val message: String
)

data class PerizinanResponse(
    val success: Boolean,
    val data: List<Perizinan>
)

data class CreatePerizinanResponse(
    val success: Boolean,
    val data: Perizinan
)