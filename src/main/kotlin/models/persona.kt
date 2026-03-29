package models

open class persona(val id: String ,val nombre: String,val rol : Int) {

}

open class admin(id: String , nombre: String, rol: Int) : persona(id, nombre, rol) {


    //funcion para eliminar usuarios

    fun eliminarusuarios(usuariosLista: MutableList<usuario>):Boolean{
        println("Bienvenido admin a quien quieres eliminar")
        println("Dime el id del usuario: ")
        var id = readLine()!!
        println("Dime el nombre: ")
        var nombre = readLine()!!
        val usuarioAEliminar = usuariosLista.find { it.id == id && it.nombre == nombre }

        if (usuarioAEliminar == null){
        println("Usuario no existe")
        return false
    }
        usuariosLista.remove(usuarioAEliminar)
        println("Usuario eliminado correctamente")
        return true

}
    fun crearNuevosUsuarios(gimnasio: gimnasio){
        println("Dime el id del nuevo usuario: ")
        var id = readLine()!!
        println("Dime el nombre: ")
        var nombre = readLine()!!
        gimnasio.agregarusuario(id,nombre)

    }}
open class usuario(id: String , nombre: String, rol: Int) : persona(id, nombre, rol) {
    //lista de ejercicios dividida por dias
    val listaejercicios = mutableMapOf<Int, MutableList<ejercicios>>()
    //lista de dieta dividida por dias
    val listadieta = mutableMapOf<Int, MutableList<alimentos>>()


    fun agregarListaEjercicios(): Boolean {

        println("Cuantos dias quieres entrenar: ")
        val diasentrenar = readLine()?.toIntOrNull() ?: 0
//hago unas condiciones para evitar cosas como que pongan 0 dias o negativos y para evitar que pongan mas de 7 dias semanales
        if (diasentrenar > 7){
            println("No puedes entrenar mas de 7 dias a la semana")
            return false
        }
        if (diasentrenar <= 0){
            println("No puedes entrenar 0 dias o menos")
            return false
        }

        for (dia in 1..diasentrenar){
            println("\nDia $dia:")
            print("Cuantos ejercicios quieres hacer este dia: ")
            val cantEjercicios = readLine()?.toIntOrNull() ?: 0

            if (cantEjercicios <= 0){
                println("No puedes hacer 0 o menos")
                continue
            }
//creo la lista de ejercicios del dia para dividir por dias los entrenos y ejercicios
            val ejerciciosDelDia = mutableListOf<ejercicios>()
            for (i in 1..cantEjercicios){
                print("Nombre del ejercicio $i: ")
                val nombreEjercicio = readLine()!!
                print("musculo a entrenar: ")
                val musculo = readLine()!!
                ejerciciosDelDia.add(ejercicios(nombreEjercicio, musculo))
            }
            //una vez calculado los dias de entreno los divido en otra lista para luego mostrar los dias por separado
            listaejercicios[dia] = ejerciciosDelDia
            println("Dia $dia agregado bien")
        }

        return true
    }


    fun verlistaejercicios(){
        println("Lista de ejercicios de $nombre")

        if (listaejercicios.isEmpty()){
            println("Lista vacia no hay ejercicios")
        }else{
            //hago un foreach que recorra la lista y muestre los dias y los entrenos
            listaejercicios.forEach{ (dia, ejerciciosDia) ->
                println("\nDia $dia:")
                ejerciciosDia.forEach { ejercicio ->
                    println("-${ejercicio.nombreEjercicio} ${ejercicio.musculoentrenado}")
                }
            }
        }
    }


    fun eliminarListaEjercicios(): Boolean{
        if (listaejercicios.isEmpty()){
            println("La lista de ejercicios no contiene nada")
            return false
        }
        //limpio con clear la lista entera
        listaejercicios.clear()
        println("Se a eliminado la lista de ejercicios bien de $nombre")
        return true
    }

//para hacer las funciones de alimentos/dieta e basicamente usado las de ejercicio cambiando los nombre y subsitutir un string por INt
    fun agregarListaDieta(): Boolean {

        println("Cuantos dias quieres hacer dieta: ")
        val diasentrenar = readLine()?.toIntOrNull() ?: 0

        if (diasentrenar > 7){
            println("No puedes hacer dieta mas de 7 dias a la semana")
            return false
        }
        if (diasentrenar <= 0){
            println("No puedes hacer dieta 0 dias o menos")
            return false
        }

        for (dia in 1..diasentrenar){
            println("\nDia $dia:")
            print("Cuantos alimentos quieres consumir este dia: ")
            val cantAlimentos = readLine()?.toIntOrNull() ?: 0

            if (cantAlimentos <= 0){
                println("No puedes poner 0 o menos")
                continue
            }

            val alimentosDelDia = mutableListOf<alimentos>()
            for (i in 1..cantAlimentos){
                print("Nombre del alimento $i: ")
                val nombreAlimento = readLine()!!
                print("Kcal del alimento: ")
                val kcalAlimento = readLine()!!.toInt()
                alimentosDelDia.add(alimentos(nombreAlimento, kcalAlimento))
            }

            listadieta[dia] = alimentosDelDia
            println("Dia $dia agregado bien")
        }

        return true
    }

    fun verListaDieta(){
        println("Lista de dieta de $nombre")

        if (listadieta.isEmpty()){
            println("Lista vacia no hay alimentos")
        }else{
            listadieta.forEach{ (dia, alimentosDia) ->
                println("\nDia $dia:")
                alimentosDia.forEach { alimento ->
                    println("-${alimento.nombreAlimento} ${alimento.kcal} kcal")
                }
            }
        }
    }


    fun eliminarListaDieta(): Boolean{
        if (listadieta.isEmpty()){
            println("La lista de dieta no contiene nada")
            return false
        }
        listadieta.clear()
        println("Se a eliminado la lista de dieta bien de $nombre")
        return true
    }
}