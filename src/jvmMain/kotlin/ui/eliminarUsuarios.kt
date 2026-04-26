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
fun eliminarUsuarios(usuarioActual: usuario, onBack: () -> Unit) {

    val usuarios = remember { mutableStateOf(repoUsuarios.findAll()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 39, 39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))

        Text(
            "Gimnasio de Igor: Eliminar usuarios",
            style = MaterialTheme.typography.h4
        )

        Spacer(Modifier.height(15.dp))

        Text(
            "Lista de usuarios actualmente",
            style = MaterialTheme.typography.h5
        )

        Spacer(Modifier.height(10.dp))

        if (usuarios.value.isEmpty()) {

            Text("No hay usuarios")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(usuarios.value) { usuario ->

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color(250, 200, 0))
                            .padding(8.dp)
                    ) {

                        Text("ID: ${usuario.id_u}")
                        Text("Nombre: ${usuario.nombre_u}")
                        Spacer(Modifier.height(5.dp))

                        Button(onClick = {
                            repoUsuarios.delete(usuario.id_u)
                            usuarios.value = repoUsuarios.findAll()
                        }) {
                            Text("Eliminar usuario")
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}


