package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun mainmenu(onSelect: (String) -> Unit) {
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
            style = MaterialTheme.typography.h3
        )
        Text(
            "Identificate",
            style = MaterialTheme.typography.h5
        )

        Spacer(Modifier.height(80.dp))
        Button( onClick = { onSelect("loginu") }) {
            Text("Iniciar sesion como usuario")
        }


        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onSelect("registrarusuario") }){
            Text("Registrar nuevo usuario")
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick ={ onSelect("LOANS")}){



            Text("Iniciar sesion como admin")
        }


    }}