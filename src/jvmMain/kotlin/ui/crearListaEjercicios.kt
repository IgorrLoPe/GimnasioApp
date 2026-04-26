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
import models.ejercicios
import state.gymState.usuario

@Composable
fun crearListaEjercicios(usuarioActual: usuario, onBack: () -> Unit) {

    var dias by remember { mutableStateOf("") }
    var diaActual by remember { mutableStateOf(1) }
    var totalDias by remember { mutableStateOf(0) }

    var nombreEjercicio by remember { mutableStateOf("") }
    var musculo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val ejerciciosDia = remember { mutableStateListOf<ejercicios>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255,39,39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Crear rutina", style = MaterialTheme.typography.h4)

        Spacer(Modifier.height(15.dp))

        if (totalDias == 0) {

            TextField(
                value = dias,
                onValueChange = { dias = it },
                label = { Text("Dias a entrenar (1-7)") }
            )

            Button(onClick = {
                val n = dias.toIntOrNull() ?: 0

                if (n in 1..7) {
                    totalDias = n
                } else {
                    mensaje = "dias invalido"
                }

            }) {
                Text("Empezar")
            }

        } else {

            Text("Dia $diaActual de $totalDias")

            TextField(
                value = nombreEjercicio,
                onValueChange = { nombreEjercicio = it },
                label = { Text("Ejercicio") }
            )

            TextField(
                value = musculo,
                onValueChange = { musculo = it },
                label = { Text("Musculo") }
            )

            Button(onClick = {
                if (nombreEjercicio.isNotEmpty() && musculo.isNotEmpty()) {
                    ejerciciosDia.add(
                        ejercicios(nombreEjercicio, musculo)
                    )
                    nombreEjercicio = ""
                    musculo = ""
                }
            }) {
                Text("Añadir")
            }

            LazyColumn(
                modifier = Modifier.height(150.dp)
            ) {
                items(ejerciciosDia) {
                    Text("${it.nombreEjercicio} - ${it.musculoentrenado}")
                }
            }

            Button(onClick = {

                if (ejerciciosDia.isEmpty()) {
                    mensaje = "Agega ejercicios"
                    return@Button
                }

                usuarioActual.listaejercicios[diaActual] =
                    ejerciciosDia.toMutableList()

                ejerciciosDia.clear()

                if (diaActual < totalDias) {
                    diaActual++
                } else {
                    repoUsuarios.save(usuarioActual)
                    mensaje = "Rutina guardada correctamente"
                }

            }) {
                Text("Guardar dia")
            }
        }

        Spacer(Modifier.height(10.dp))

        Text(mensaje)

        Spacer(Modifier.height(15.dp))

        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}