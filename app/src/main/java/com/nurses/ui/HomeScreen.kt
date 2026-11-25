package com.nurses.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nurses.R

/**
 * Pantalla inicial (Home) de la aplicación Nurses.
 *
 * PARÁMETROS onClick:
 * Esta pantalla NO sabe navegar por sí misma. En lugar de eso recibe tres funciones
 * (lambdas de tipo `() -> Unit`, es decir, funciones sin parámetros que no devuelven nada).
 * Cuando el usuario pulsa un botón, la pantalla simplemente "avisa" llamando a la función
 * correspondiente. Quien use HomeScreen (normalmente MainActivity, donde se monta la
 * navegación) decide qué hacer: ir a Login, a la lista, a la búsqueda...
 *
 * Ventajas de hacerlo así:
 *  - La pantalla está desacoplada: no depende de cómo esté hecha la navegación.
 *  - Se puede ver en el @Preview pasando funciones vacías `{}`.
 *  - Cada miembro del grupo puede trabajar en su pantalla sin tocar las de los demás.
 *
 * @param onLoginClick  se llama al pulsar "Iniciar sesión".
 * @param onListClick   se llama al pulsar "Listar enfermeros".
 * @param onSearchClick se llama al pulsar "Buscar enfermero".
 */

@Composable
fun HomeScreen(
    onLoginClick: () -> Unit,
    onListClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    // ESTADO CON mutableState:
    // `mutableStateOf(false)` crea un valor "observable" por Compose. Cada vez que cambia,
    // Compose vuelve a ejecutar (recompone) las partes de la pantalla que lo leen, y así
    // la interfaz se actualiza sola.
    // `remember { ... }` hace que el valor se conserve entre recomposiciones; sin remember,
    // cada vez que se redibujara la pantalla el valor volvería a ser false.
    // `by` (delegado) nos deja usar `showWelcome` como un Boolean normal en vez de
    // escribir `showWelcome.value`.
    var showWelcome by remember { mutableStateOf(false) }

    // TEXTOS Y COLORES:
    // Ningún texto ni color está escrito a mano en el código. Los textos se leen de
    // res/values/strings.xml con stringResource() y los colores de res/values/colors.xml
    // con colorResource(). Así:
    //  - se pueden cambiar en un solo sitio sin tocar el código,
    //  - la app se puede traducir creando otro strings.xml (por ejemplo values-ca/),
    //  - lo pide el enunciado de la práctica.
    // Todos llevan el prefijo "home_" para no chocar con los recursos de los compañeros.

    // Column coloca sus hijos uno debajo de otro.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.home_background))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // IMAGEN / ICONO: vector drawable propio (res/drawable/home_ic_nurse.xml).
        // contentDescription es el texto que leen los lectores de pantalla (accesibilidad).
        Image(
            painter = painterResource(R.drawable.home_ic_nurse),
            contentDescription = stringResource(R.string.home_icon_desc),
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título de la app
        Text(
            text = stringResource(R.string.home_title),
            color = colorResource(R.color.home_text),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        // Subtítulo
        Text(
            text = stringResource(R.string.home_subtitle),
            color = colorResource(R.color.home_text_secondary),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // INTERACCIÓN CON mutableState:
        // Al pulsar este botón invertimos el valor de showWelcome (true <-> false).
        // Como es un mutableState, Compose redibuja la pantalla automáticamente:
        // cambia el texto del botón y aparece/desaparece el mensaje de bienvenida.
        OutlinedButton(
            onClick = { showWelcome = !showWelcome },
            border = BorderStroke(1.dp, colorResource(R.color.home_primary)),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colorResource(R.color.home_primary)
            )
        ) {
            Text(
                text = if (showWelcome) {
                    stringResource(R.string.home_btn_hide_welcome)
                } else {
                    stringResource(R.string.home_btn_show_welcome)
                }
            )
        }

        // El mensaje solo se dibuja si showWelcome es true.
        if (showWelcome) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.home_welcome_message),
                color = colorResource(R.color.home_text),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.home_welcome_bg),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BOTONES DE NAVEGACIÓN:
        // Cada botón solo llama a la función que nos han pasado por parámetro.
        // La navegación real la monta quien haga MainActivity.
        HomeNavButton(
            text = stringResource(R.string.home_btn_login),
            onClick = onLoginClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        HomeNavButton(
            text = stringResource(R.string.home_btn_list),
            onClick = onListClick
        )
        Spacer(modifier = Modifier.height(12.dp))
        HomeNavButton(
            text = stringResource(R.string.home_btn_search),
            onClick = onSearchClick
        )
    }
}

/**
 * Botón reutilizable para los tres botones de navegación, así no repetimos
 * el mismo estilo tres veces. Es `private` porque solo se usa en este archivo.
 */
@Composable
private fun HomeNavButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.home_primary),
            contentColor = colorResource(R.color.home_on_primary)
        )
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

/**
 * PREVIEW:
 * Permite ver la pantalla en Android Studio (vista Split o Design) sin ejecutar la app.
 * Como el Preview no tiene navegación, pasamos funciones vacías `{}` a los tres parámetros:
 * los botones se pueden pulsar en modo interactivo pero no hacen nada.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onLoginClick = {},
        onListClick = {},
        onSearchClick = {}
    )
}
