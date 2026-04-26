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
import models.alimentos
import models.ejercicios
import state.gymState.usuario

@Composable
fun crearListaAlimentoss(usuarioActual: usuario, onBack: () -> Unit) {

    var dias by remember { mutableStateOf("") }
    var diaActual by remember { mutableStateOf(1) }
    var totalDias by remember { mutableStateOf(0) }

    var nombreAlimento by remember { mutableStateOf("") }

    var kcal by remember { mutableStateOf(0) }
    var kcalInput by remember { mutableStateOf("") }

    var mensaje by remember { mutableStateOf("") }

    val listaTemporal = remember { mutableStateListOf<Pair<String, Int>>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255,39,39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Crear dieta", style = MaterialTheme.typography.h4)

        Spacer(Modifier.height(15.dp))

        if (totalDias == 0) {

            TextField(
                value = dias,
                onValueChange = { dias = it },
                label = { Text("Dias a hacer dieta (1-7)") }
            )

            Button(onClick = {
                val n = dias.toIntOrNull() ?: 0

                if (n in 1..7) {
                    totalDias = n
                    mensaje = ""
                } else {
                    mensaje = "Dias no validos"
                }

            }) {
                Text("Empezar")
            }

        } else {

            Text("Dia $diaActual de $totalDias")

            TextField(
                value = nombreAlimento,
                onValueChange = { nombreAlimento = it },
                label = { Text("Alimento") }
            )

            TextField(
                value = kcalInput,
                onValueChange = { value ->

                    if (value.isEmpty()) {
                        kcalInput = ""
                        kcal = 0
                        mensaje = ""
                    } else if (value.all { it.isDigit() }) {
                        kcalInput = value
                        kcal = value.toInt()
                        mensaje = ""
                    } else {
                        mensaje = "Tienen que ser numeros"
                    }
                },
                label = { Text("Kcal") }
            )

            Button(onClick = {
                if (nombreAlimento.isNotEmpty() && kcal > 0) {

                    listaTemporal.add(nombreAlimento to kcal)

                    nombreAlimento = ""
                    kcalInput = ""
                    kcal = 0
                    mensaje = ""

                } else {
                    mensaje = "Completa los campos correctamente"
                }
            }) {
                Text("Añadir")
            }

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.height(150.dp)
            ) {
                items(listaTemporal) {
                    Text("${it.first} - ${it.second} kcal")
                }
            }

            Button(onClick = {

                if (listaTemporal.isEmpty()) {
                    mensaje = "Añade alimentos"
                    return@Button
                }

                val listaDia = mutableListOf<alimentos>()

                listaTemporal.forEach {
                    listaDia.add(alimentos(it.first, it.second))
                }

                usuarioActual.listadieta[diaActual] = listaDia

                listaTemporal.clear()

                if (diaActual < totalDias) {
                    diaActual++
                } else {
                    repoUsuarios.save(usuarioActual)
                    mensaje = "Dieta guardada bien"
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
