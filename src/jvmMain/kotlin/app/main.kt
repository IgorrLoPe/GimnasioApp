package app

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import state.gymState.usuario
import state.gymState.admin
import state.gymState.persona
import models.gimnasio
import androidx.compose.runtime.LaunchedEffect
import regexexpressions.regex
import kotlinx.serialization.builtins.serializer
import state.gymState
import state.gymState.UsuarioRepository
import ui.loginusuario
import ui.mainmenu
import registrarusuario
import ui.loginadmin
import ui.menuUsuario
import ui.menuAdmin
val Gimnasio = gimnasio()
val gymestado = gymState(Gimnasio)

val repoUsuarios = UsuarioRepository(
    "usuarios.json",
    usuario.serializer()
)

//defino una excepcion para valores duplicados
class ElementDuplicatException(message: String) : Exception(message)

//para evitar elementos vacios
class ElementVacio(message2: String) : Exception(message2)

//creo un verificador de duplicados que revise las id de los usuarios y mande un mensaje de excpecion si falla
fun verificarduplicados(id: String, usuarioslista: MutableList<usuario>) {
    Gimnasio.usuarioslista.forEach {
        if (it.id == id) {
            throw ElementDuplicatException("error: Este id ya existe pon otro")
        }
    }
}

fun verficarNoElementosVacios(id: String, nombre: String) {
    if (id == "" || nombre == "") {
        throw ElementVacio("Error: has mandado datos vacios debes poner cosas")
    }
}

fun main() = application {


    val adminInicial = usuario("1", "igor", 0)
    repoUsuarios.save(adminInicial)

    Window(
        onCloseRequest = ::exitApplication,
        title = "Biblioteca"
    ) {
        MaterialTheme(colors = lightColors(primary = Color(0, 0, 0))) {
            App()
        }
    }
}

@Composable
fun App() {

    var screen by remember { mutableStateOf("MENU") }

    var usuarioiniciado by remember { mutableStateOf<usuario?>(null) }
    var admininiciado by remember { mutableStateOf<admin?>(null) }

    LaunchedEffect(Unit) {
        Gimnasio.usuarioslista.clear()
        Gimnasio.usuarioslista.addAll(repoUsuarios.findAll())
    }

    when (screen) {

        "MENU" -> mainmenu { screen = it }

        "loginusuario" -> loginusuario(
            onLoginOk = { user ->
                usuarioiniciado = user
                screen = "menuUsuario"
            },
            onBack = { screen = "MENU" }
        )

        "registrarusuario" -> registrarusuario(
            onLoginOk = { user -> usuarioiniciado = user
                screen = "menuUsuario" }, onBack = { screen = "MENU" })

        "loginadmin" -> loginadmin(onLoginAdmin = { screen = "menuAdmin" }, onBack = { screen = "MENU" })



        "menuUsuario" -> menuUsuario(user = usuarioiniciado!!, onBack = { screen = "MENU" })
        "menuAdmin" -> menuAdmin(onBack = { screen = "MENU" })



    }





}