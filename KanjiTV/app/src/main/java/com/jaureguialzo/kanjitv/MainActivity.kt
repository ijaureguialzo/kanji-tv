@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.jaureguialzo.kanjitv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.google.gson.Gson
import com.jaureguialzo.kanjitv.ui.theme.KanjiTVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonString = assets.open("kanji.json").bufferedReader().use { it.readText() }
        val datos = Gson().fromJson(jsonString, Array<Kanji>::class.java)

        setContent {
            PantallaPrincipal(datos)
        }
    }
}

@Composable
fun Texto(
    text: String, color: Color, fontSize: TextUnit, fontFamily: FontFamily = FontFamily.SansSerif
) {
    Text(
        text = text, style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontFamily = fontFamily,
        )
    )
}

@Composable
fun PantallaPrincipal(datos: Array<Kanji>) {

    val n = (0 until datos.size).random();

    KanjiTVTheme {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texto(datos[n].kanji, Color.White, 80.sp, FontFamily.Serif)

            Row {
                for ((i, kun) in datos[n].kun.withIndex()) {
                    if (i > 0 && i < datos[n].kun.size) {
                        Texto(" · ", Color(253, 216, 53, 255), 20.sp)
                    }
                    Texto(kun, Color(253, 216, 53, 255), 20.sp)
                }
            }

            Row {
                for ((i, on) in datos[n].on.withIndex()) {
                    if (i > 0 && i < datos[n].on.size) {
                        Texto(" · ", Color.White, 20.sp)
                    }
                    Texto(on, Color.White, 20.sp)
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = datos[n].trazos.toString(),
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 10.sp,
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .drawBehind {
                            drawCircle(
                                color = Color.Black, radius = this.size.maxDimension
                            )
                        },
                )
                Texto(datos[n].notas, Color.LightGray, 10.sp)
            }

            Row {
                for ((i, significado) in datos[n].significados.withIndex()) {
                    if (i > 0 && i < datos[n].significados.size) {
                        Texto(" · ", Color.White, 20.sp)
                    }
                    Texto(significado, Color.White, 20.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val datos = arrayOf<Kanji>(
        Kanji(
            id = 1,
            kanji = "山",
            kun = arrayOf<String>("やま"),
            on = arrayOf<String>("サン"),
            trazos = 3,
            notas = "El primer trazo es el vertical central",
            significados = arrayOf<String>("Montaña")
        )
    )

    PantallaPrincipal(datos)
}
