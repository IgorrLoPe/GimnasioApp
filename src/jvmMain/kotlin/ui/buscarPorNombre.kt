package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.repoUsuarios
import state.gymState.usuario

@Composable
fun buscarPorNombre(usuarioActual: usuario, onBack: () -> Unit) {

    var idBuscado by remember { mutableStateOf("") }
    var resultados by remember { mutableStateOf(listOf<usuario>()) }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 39, 39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Gimnasio igor como admin: Buscar por nombre",
            style = MaterialTheme.typography.h4
        )

        Spacer(Modifier.height(20.dp))

        TextField(
            value = idBuscado,
            onValueChange = { idBuscado = it },
            label = { Text("Introduce nombre") }
        )

        Spacer(Modifier.height(10.dp))

        Button(onClick = {

            val encontrado = repoUsuarios.findAll()
                .filter { it.nombre_u == idBuscado }

            resultados = encontrado

            mensaje = if (encontrado.isEmpty()) {
                "No hay usuarios con este nombre"
            } else {
                "Usuario encontrado"
            }

        }) {
            Text("Buscar")
        }

        Spacer(Modifier.height(10.dp))
        Text(mensaje)

        Spacer(Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(resultados) { usuario ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color(250, 200, 0))
                        .padding(8.dp)
                ) {
                    Text("ID: ${usuario.id_u}")
                    Text("Nombre: ${usuario.nombre_u}")
                }
                Spacer(Modifier.height(10.dp))
            }
        }
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}



