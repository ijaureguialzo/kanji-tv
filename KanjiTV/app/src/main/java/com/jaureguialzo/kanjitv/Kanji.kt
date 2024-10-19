package com.jaureguialzo.kanjitv

data class Kanji(
    val id: Int,
    val kanji: String,
    val kun: Array<String>,
    val on: Array<String>,
    val trazos: Int,
    val notas: String,
    val significados: Array<String>,
)
