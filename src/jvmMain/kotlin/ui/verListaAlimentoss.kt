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
fun verListaAlimentoss(usuarioActual: usuario, onBack: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 39, 39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Gimnasio igor: lista alimentos",
            style = MaterialTheme.typography.h4
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Rutina de ${usuarioActual.nombre_u}",
            style = MaterialTheme.typography.h5
        )

        Spacer(Modifier.height(15.dp))

        if (usuarioActual.listadieta.isEmpty()) {

            Text("No hay alimentos guardados")

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                usuarioActual.listadieta.forEach  { (dia, lista) ->

                    item {
                        Text(
                            "Dia $dia",
                            style = MaterialTheme.typography.h6
                        )
                        Spacer(Modifier.height(6.dp))
                    }

                    items(lista) { ejercicio ->
                        Text("- ${ejercicio.nombreAlimento} (${ejercicio.kcal})")
                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = onBack) {
            Text("Volver")
        }
    }
}