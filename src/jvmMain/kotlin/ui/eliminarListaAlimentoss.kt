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
import state.gymState
import state.gymState.usuario

@Composable
fun eliminarListaAlimentoss(usuarioActual: usuario, onBack: () -> Unit) {

    var mensaje by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 39, 39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Gimnasio igor: Eliminar lista alimentos",
            style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.height(35.dp))
        Button(onClick = {
            val ok = usuarioActual.eliminarListaDieta()
            mensaje = (if (ok) "Lista borrada" else "No hay nada en la lista")
        }) {
            Text("Eliminar lista de alimentos de tu usuario")
        }

        Spacer(Modifier.height(35.dp))
        Button(onClick = onBack) {
            Text("Volver al menu")
        }


    }


}