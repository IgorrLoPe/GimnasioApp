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
fun loginusuario(
    onLoginOk: (usuario) -> Unit,
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
            "Inicio de sesion",
            style = MaterialTheme.typography.h3
        )

        var id by remember { mutableStateOf("") }
        var nombre by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }

        Column {

            TextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Spacer(Modifier.height(8.dp))

            Button(onClick = {

                try {
                    verficarNoElementosVacios(id, nombre)

                    val user = repoUsuarios.findById(id)

                    if (user != null && user.nombre_u == nombre) {
                        mensaje = "Login correcto: ${user.nombre_u}"
                        onLoginOk(user)

                    } else {
                        mensaje = "Login fallido"
                    }

                } catch (e: ElementVacio) {
                    mensaje = e.message ?: "Error"
                }

            }) {
                Text("Iniciar sesión")
            }

            Spacer(Modifier.height(8.dp))

            Text(mensaje)
        }
        Button(onClick = { onBack() }) {
            Text("Volver al menu")
        }
    }


}