package com.example.gofit_mobile.api

import com.example.gofit_mobile.model.*

data class LoginResponse(
    val message: String,
    val data: Member,
    val token: String,
    val kode: String
)

data class MemberResponse(
    val data: Member
)

data class LoginMoResponse(
    val message: String,
    val user: MO,
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

data class PresensiKelasResponse(
    val success: Boolean,
    val data: List<PresensiKelas>
)

data class PresensiGymResponse(
    val success: Boolean,
    val data: List<PresensiGym>
)

data class PresensiInsResponse(
    val success: Boolean,
    val data: List<PresensiInstruktur>
)

data class CreatePerizinanResponse(
    val success: Boolean,
    val data: Perizinan
)

data class CreatePresensiKelasResponse(
    val message: String,
    val data: PresensiKelas
)

data class CreatePresensiGymResponse(
    val message: String,
    val data: PresensiGym
)

data class CreatePresensiInsResponse(
    val message: String,
    val data: PresensiInstruktur
)

data class ProfileInsResponse(
    val success: Boolean,
    val data: PresensiInstruktur
)

data class TerlambatResponse(
    val success: Boolean,
    val data: String
)