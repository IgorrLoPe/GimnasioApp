package regexexpressions

class regex {

    fun validarusuario(nombre: String): Boolean {
        //Minimo el nombre debe tener una mayuscula para crear nuevos usuarios
        val nombreRegex = Regex(".*[A-Z].*")
        return nombreRegex.matches(nombre)
    }
}