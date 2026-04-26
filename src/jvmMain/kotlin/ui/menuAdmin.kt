package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import app.ElementVacio
import app.verficarNoElementosVacios
import app.repoUsuarios
import state.gymState
import state.gymState.usuario
@Composable

fun menuAdmin(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier

            .fillMaxSize()
            //sirve para cambiar el fondo de color e utilizado el sistema rgb debido a que es el que se utilizar
            .background(Color(255, 39, 39, 255))
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))
        Text(
            "Gimanasio de Igor",
            style = MaterialTheme.typography.h4
        )
        Text(
            "Bienvenido al menu de Admins",
            style = MaterialTheme.typography.h6
        )

    }}