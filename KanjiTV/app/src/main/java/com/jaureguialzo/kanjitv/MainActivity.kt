@file:OptIn(ExperimentalTvMaterial3Api::class)

package com.jaureguialzo.kanjitv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import com.jaureguialzo.kanjitv.ui.theme.KanjiTVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaPrincipal()
        }
    }
}

@Composable
fun Texto(
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontFamily: FontFamily = FontFamily.SansSerif
) {
    Text(
        text = text,
        style = TextStyle(
            color = color,
            fontSize = fontSize,
            fontFamily = fontFamily,
        )
    )
}

@Composable
fun PantallaPrincipal() {
    KanjiTVTheme {
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Texto("食", Color.White, 80.sp, FontFamily.Serif)
            Texto("たべる", Color(253, 216, 53, 255), 20.sp)
            Texto("ショク", Color.White, 20.sp)
            Column(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 10.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "9",
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 10.sp,
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .drawBehind {
                            drawCircle(
                                color = Color.Black,
                                radius = this.size.maxDimension
                            )
                        },
                )
                Texto("El séptimo trazo es el gancho de la izquierda", Color.LightGray, 10.sp)
            }
            Texto("Comer", Color.White, 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PantallaPrincipal()
}
