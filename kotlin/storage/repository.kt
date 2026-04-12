package storage

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File
import models.usuario

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
        if (!file.exists()){
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
        return readFromFile().find { it.id == id }
    }

    //para buscar en el json por nombre

    fun findBynombre(nombre: String): usuario? {
        return readFromFile().find { it.nombre == nombre }
    }
}