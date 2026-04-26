package state
import androidx.compose.runtime.mutableStateListOf
import app.Gimnasio
import app.repoUsuarios
import com.google.gson.GsonBuilder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.serializer
import models.alimentos
import models.ejercicios
import java.io.File
import models.gimnasio

import kotlin.collections.forEach
import app.Gimnasio
import app.repoUsuarios
import regexexpressions.regex
import state.gymState.UsuarioRepository

//creo esta clase para actualizar la aplicacion a tiempo real y facilitar llamar a las funciones que usare en el gui
class gymState(val gimnasio: gimnasio) {

    class UsuarioRepository(
        private val fileName: String,
        private val serializer: KSerializer<usuario>
    ) {

        private val json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

        private fun readFromFile(): MutableList<usuario> {
            val file = File(fileName)
//en caso de no existir devuelve la lista sin nada
            if (!file.exists()) {
                println("No existia el actualmente")
                return mutableListOf()
            }

            val content = file.readText()
            //en caso de que el archivo este vacio devuelve la lista
            if (content.isBlank()) return mutableListOf()
//si todo va bien devuelve todo ya serializado
            return json.decodeFromString(
                ListSerializer(serializer),
                content
            ).toMutableList()
        }

        private fun writeToFile(items: List<usuario>) {
            val file = File(fileName)

            val jsonString = json.encodeToString(
                ListSerializer(serializer),
                items
            )

            file.writeText(jsonString)
        }

        //guarda los datos del usuario
        fun save(usuario: usuario) {
            val users = readFromFile()
//revisa con el indice si existen usuarios con el mismo id
            val indic = users.indexOfFirst { it.id == usuario.id }
//en caso de no encontrarlo devuelve -1 pero si lo encuentra devuleve la posicion
            if (indic != -1) {
                users[indic] = usuario
            } else {
                users.add(usuario)
            }

            writeToFile(users)
        }

        //lee el json los usuairos
        fun findAll(): List<usuario> {
            return readFromFile()
        }

        fun delete(id: String) {
            val users = readFromFile()
            val filtered = users.filterNot { it.id == id }
            writeToFile(filtered)
        }

        //para buscar en el json por id
        fun findById(id: String): usuario? {
            val saved = readFromFile().find { it.id == id }
            return saved?.let { usuarioGuardado ->
                val enMemoria = Gimnasio.usuarioslista.find { it.id_u == usuarioGuardado.id_u }
                enMemoria ?: usuarioGuardado
            }
        }

        //para buscar en el json por nombre

        fun findBynombre(nombre: String): usuario? {
            return readFromFile().find { it.nombre == nombre }
        }


        fun agregarusuario(gimnasio: gimnasio, id: String, nombre: String): Boolean {

            gimnasio.usuarioslista.forEach {
                if (it.id == id)
                    return false
            }
            gimnasio.usuarioslista.add(gymState.usuario(id,nombre,0))
            return true
        }


    }



    @Serializable
    open class persona(
        val id: String,
        val nombre: String,
        val rol: Int
    )


    data class admin(
        val id_admin: String,
        val nombre_admin: String,
        val rol_admin: Int
    ) : persona(id_admin, nombre_admin, rol_admin) {



        val repoUsuarios = UsuarioRepository(
            "usuarios.json",
            usuario.serializer()
        )
        val miRegex = regexexpressions.regex()

        fun eliminarusuarios(usuariosLista: MutableList<gymState.usuario>): Boolean {
            println("Bienvenido admin a quien quieres eliminar")
            println("Dime el id del usuario: ")
            val id = readLine()!!

            println("Dime el nombre: ")
            val nombre = readLine()!!

            val usuarioAEliminar = usuariosLista.find { it.id == id && it.nombre == nombre }

            if (usuarioAEliminar == null) {
                println("Usuario no existe")
                return false
            }

            usuariosLista.remove(usuarioAEliminar)
            //para eliminar usuarios tambien del json
            repoUsuarios.delete(usuarioAEliminar.id)
            println("Usuario eliminado correctamente")
            return true
        }

        fun crearNuevosUsuarios(gimnasio: gimnasio) {

            println("Dime el id del nuevo usuario: ")
            val id = readLine()!!
            println("Dime el nombre (minimo una mayuscula): ")
            val nombre = readLine()!!
            if (miRegex.validarusuario(nombre)) {

                val resultado = repoUsuarios.agregarusuario(gimnasio, id, nombre)

                if (resultado) {
                    println("Usuario $nombre con id $id creado correctamente")
                } else {
                    println("Ya existe un usuario con ese ID")
                }
            } else {
                println("No cumple requisitos")
            }
        }

        fun filtrarporId() {
            println("Dime que id quieres bucar")
            var iddicha = readLine()!!
            val encontrado = Gimnasio.usuarioslista.filter { it.id == iddicha }
            if (encontrado.isNotEmpty()) {

                encontrado.forEach { usuario ->
                    repoUsuarios.findById(iddicha)
                    println("ID: ${usuario.id}, Nombre: ${usuario.nombre}")

                }
            } else {
                println("No hay usuarios con esta id")
            }
        }

        fun filtrarPorNombre() {
            println("Dime que nombre quieres buscar")
            val nombredicho = readLine()!!
            val encontrado = Gimnasio.usuarioslista.filter { it.nombre == nombredicho }
            if (encontrado.isNotEmpty()) {
                encontrado.forEach { usuario ->
                    repoUsuarios.findBynombre(nombredicho)
                    println("ID: ${usuario.id}, Nombre: ${usuario.nombre}")
                }
            } else {
                println("No hay usuarios con esta id")
            }
        }
    }

    @Serializable
    data class usuario(
        val id_u: String,
        val nombre_u: String,
        val rol_u: Int,
        //gracias a esto el json recopilara las listas de cada usuario
        val listaejercicios: MutableMap<Int, MutableList<ejercicios>> = mutableMapOf(),
        val listadieta: MutableMap<Int, MutableList<alimentos>> = mutableMapOf()
    ) : persona(id_u, nombre_u, rol_u) {

        fun agregarListaEjercicios(): Boolean {
            println("Cuantos dias quieres entrenar: ")
            val diasentrenar = readLine()?.toIntOrNull() ?: 0

            if (diasentrenar > 7) {
                println("No puedes entrenar mas de 7 dias a la semana")
                return false
            }
            if (diasentrenar <= 0) {
                println("No puedes entrenar 0 dias o menos")
                return false
            }

            for (dia in 1..diasentrenar) {
                println("\nDia $dia:")
                print("Cuantos ejercicios quieres hacer este dia: ")
                val cantEjercicios = readLine()?.toIntOrNull() ?: 0

                if (cantEjercicios <= 0) {
                    println("No puedes hacer 0 o menos")
                    continue
                }

                val ejerciciosDelDia = mutableListOf<ejercicios>()
                for (i in 1..cantEjercicios) {
                    print("Nombre del ejercicio $i: ")
                    val nombreEjercicio = readLine()!!
                    print("musculo a entrenar: ")
                    val musculo = readLine()!!
                    ejerciciosDelDia.add(ejercicios(nombreEjercicio, musculo))
                }
                listaejercicios[dia] = ejerciciosDelDia
                //guarda la lista de ejercicios
                repoUsuarios.save(this)
                println("Dia $dia agregado bien")
            }
            return true
        }

        fun verlistaejercicios() {
            println("Lista de ejercicios de $nombre")
            if (listaejercicios.isEmpty()) {
                println("Lista vacia no hay ejercicios")
            } else {
                listaejercicios.forEach { (dia, ejerciciosDia) ->
                    println("\nDia $dia:")
                    ejerciciosDia.forEach { ejercicio ->
                        println("-${ejercicio.nombreEjercicio} ${ejercicio.musculoentrenado}")
                    }
                }
            }
        }

        fun eliminarListaEjercicios(): Boolean {
            if (listaejercicios.isEmpty()) {
                return false
            }
            val idLista = id
            listaejercicios.clear()
            repoUsuarios.save(this)

            return true
        }

        fun agregarListaDieta(): Boolean {
            println("Cuantos dias quieres hacer dieta: ")
            val diasentrenar = readLine()?.toIntOrNull() ?: 0

            if (diasentrenar > 7) {
                println("No puedes hacer dieta mas de 7 dias a la semana")
                return false
            }
            if (diasentrenar <= 0) {
                println("No puedes hacer dieta 0 dias o menos")
                return false
            }

            for (dia in 1..diasentrenar) {
                println("\nDia $dia:")
                print("Cuantos alimentos quieres consumir este dia: ")
                val cantAlimentos = readLine()?.toIntOrNull() ?: 0

                if (cantAlimentos <= 0) {
                    println("No puedes poner 0 o menos")
                    continue
                }

                val alimentosDelDia = mutableListOf<alimentos>()
                for (i in 1..cantAlimentos) {
                    print("Nombre del alimento $i: ")
                    val nombreAlimento = readLine()!!
                    print("Kcal del alimento: ")
                    val kcalAlimento = readLine()?.toIntOrNull() ?: 0
                    alimentosDelDia.add(alimentos(nombreAlimento, kcalAlimento))
                }
                listadieta[dia] = alimentosDelDia
                repoUsuarios.save(this)
                println("Dia $dia agregado bien")
            }
            return true
        }

        fun verListaDieta() {
            println("Lista de dieta de $nombre")
            if (listadieta.isEmpty()) {
                println("Lista vacia no hay alimentos")
            } else {
                listadieta.forEach { (dia, alimentosDia) ->
                    println("\nDia $dia:")
                    alimentosDia.forEach { alimento ->
                        println("-${alimento.nombreAlimento} ${alimento.kcal} kcal")
                    }
                }
            }
        }

        fun eliminarListaDieta(): Boolean {
            if (listadieta.isEmpty()) {
                println("La lista de dieta no contiene nada")
                return false
            }
            listadieta.clear()
            repoUsuarios.save(this)

            println("Se a eliminado la lista de dieta bien de $nombre")
            return true
        }


    }
    }



