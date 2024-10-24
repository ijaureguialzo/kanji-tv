@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.jaureguialzo.kanjitv

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.google.gson.Gson
import com.jaureguialzo.kanjitv.ui.theme.KanjiTVTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

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
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun PantallaPrincipal(datos: Array<Kanji>) {

    val segundos = 20
    val retardo = 5

    var n = remember { mutableIntStateOf(0) }
    var mostrar_kanji = remember { mutableStateOf(false) }
    var mostrar_lecturas = remember { mutableStateOf(false) }
    var mostrar_trazos = remember { mutableStateOf(false) }
    var mostrar_traduccion = remember { mutableStateOf(false) }

    timer(initialDelay = 2000L, period = segundos * 1000L) {
        n.intValue = (0 until datos.size).random()

        mostrar_kanji.value = true;
        mostrar_lecturas.value = false;
        mostrar_trazos.value = false;
        mostrar_traduccion.value = false;

        runBlocking {
            delay(retardo * 1000L)
        }

        mostrar_kanji.value = true;
        mostrar_lecturas.value = true;
        mostrar_trazos.value = true;
        mostrar_traduccion.value = true;
    }

    val kyokashoFontFamily = FontFamily(
        Font("Kyokasho.ttc", LocalContext.current.assets)
    )

    val escala = 2

    KanjiTVTheme {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.alpha(if (mostrar_kanji.value) 1f else 0f)
            ) {
                Texto(datos[n.intValue].kanji, Color.White, (escala * 80).sp, kyokashoFontFamily)
            }

            Spacer(modifier = Modifier.height((escala * 10).dp))

            Row(
                modifier = Modifier.alpha(if (mostrar_lecturas.value) 1f else 0f)
            ) {
                Texto("", Color(253, 216, 53, 255), (escala * 20).sp)
                for ((i, kun) in datos[n.intValue].kun.withIndex()) {
                    if (i > 0 && i < datos[n.intValue].kun.size) {
                        Texto(" · ", Color(253, 216, 53, 255), (escala * 20).sp)
                    }
                    Texto(kun, Color(253, 216, 53, 255), (escala * 20).sp)
                }
            }

            Row(
                modifier = Modifier.alpha(if (mostrar_lecturas.value) 1f else 0f)
            ) {
                Texto("", Color.White, (escala * 20).sp)
                for ((i, on) in datos[n.intValue].on.withIndex()) {
                    if (i > 0 && i < datos[n.intValue].on.size) {
                        Texto(" · ", Color.White, (escala * 20).sp)
                    }
                    Texto(on, Color.White, (escala * 20).sp)
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = (escala * 5).dp, bottom = (escala * 10).dp)
                    .wrapContentSize()
                    .alpha(if (mostrar_trazos.value) 1f else 0f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = datos[n.intValue].trazos.toString(),
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = (escala * 10).sp,
                    ),
                    modifier = Modifier
                        .padding((escala * 10).dp)
                        .drawBehind {
                            drawCircle(
                                color = Color.DarkGray, radius = this.size.maxDimension
                            )
                        },
                )
                Row(modifier = Modifier.padding(horizontal = 100.dp)) {
                    Texto(datos[n.intValue].notas, Color.LightGray, (escala * 10).sp)
                }
            }

            Row(
                modifier = Modifier.alpha(if (mostrar_traduccion.value) 1f else 0f)
            ) {
                for ((i, significado) in datos[n.intValue].significados.withIndex()) {
                    if (i > 0 && i < datos[n.intValue].significados.size) {
                        Texto(" · ", Color.White, (escala * 20).sp)
                    }
                    Texto(significado, Color.White, (escala * 20).sp)
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
