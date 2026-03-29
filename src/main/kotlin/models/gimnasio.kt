package models


class gimnasio {
//creo una lista donde guardare todos los usuarios
    val usuarioslista: MutableList<usuario> = mutableListOf()
    val adminlista: MutableList<admin> = mutableListOf()
    fun agregarusuario(id: String, nombre: String): Boolean{
        val usuarios = usuario(id,nombre,0)
        usuarioslista.forEach {
            if (it.id == id)
                return false
        }
        usuarioslista.add(usuarios)
        return true
    }
//funcion que revisa con un foreach si el id existe
    fun verficarinicio(id: String, nombre: String): Boolean{
        usuarioslista.forEach {
            if (it.id == id && it.nombre == nombre){
                println("Bienvenido ${it.nombre}")
                return true
            }
        }
        println("No hay una cuenta con esos datos")
        return false
    }
//aun no e creado la lista de admins entonces unicamente reviso que el nombre y el id coincidan
    fun verficaradmin(id: String, nombre: String): Boolean{

        if (id == "1234" && nombre == "Admin"){
            println("Has iniciado sesion como admin")
            return true

        }
        println("Has fallado el inicio de sesion como admin")
        return false

    }


}