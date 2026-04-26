import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import app.gymestado
import app.ElementDuplicatException
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
fun registrarusuario(onLoginOk: (usuario) -> Unit, onBack: () -> Unit) {

    var id by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 39, 39))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(50.dp))

        Text("Registrar usuario")

        TextField(
            value = id,
            onValueChange = { id = it },
            label = { Text("ID") }
        )

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {

            try {
                verficarNoElementosVacios(id, nombre)

                if (regexexpressions.regex().validarusuario(nombre)) {

                    if (gymestado.gimnasio.usuarioslista.any { it.id == id }) {
                        throw ElementDuplicatException("ID ya existe")
                    }

                    val nuevo = usuario(id, nombre, 1)

                    gymestado.gimnasio.usuarioslista.add(nuevo)
                    repoUsuarios.save(nuevo)

                    mensaje = "Usuario creado correctamente"

                    onLoginOk(nuevo)

                } else {
                    mensaje = "El nombre no cumple reglas"
                }

            } catch (e: ElementVacio) {
                mensaje = e.message ?: "Error vacío"
            } catch (e: ElementDuplicatException) {
                mensaje = e.message ?: "Duplicado"
            }

        }) {
            Text("Registrar")
        }

        Spacer(Modifier.height(8.dp))

        Text(mensaje)

        Button(onClick = onBack) {
            Text("Volver al menu")
        }
    }
}