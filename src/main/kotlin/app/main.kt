package app
import models.admin
import models.gimnasio
import models.persona
import models.usuario

val Gimnasio = gimnasio()

fun main() {
    //variable que inicia como 0 y una vez cambia te saca del login si es igual a 1 eres un usuario normal y si es 2 eres admin
    var usuarioiniciado = 0
    var PersonaActual: usuario? = null
    var PersonaAdmin: admin? = null
    Gimnasio.agregarusuario("888","random")
while(usuarioiniciado == 0){
  println("Quieres registrarte o iniciar sesion")
  println("Pulsa 1 para Registrarte un nuevo usuario")
  println("Pulsa 2 para iniciar sesion como usuario")
  println("Pulsa 3 para iniciar sesion como administrador")
    print("Pulsa la opcion que quieras: ")

  when (readLine()?.trim()) {
    "1" ->{
      println("Bienvenido a registrar un usuario")
      println("ingresa el id de tu usuario: ")
     var idingresada = readLine()!!
      println("ingresa el nombre de usuario: ")
      var nombreingresado = readLine()!!
        if (Gimnasio.agregarusuario(idingresada, nombreingresado)) {
            println("Usuario creado bien")
            PersonaActual = usuario(idingresada, nombreingresado, 1)
            usuarioiniciado = 1
        } else {
            println("Id ya existe")

    }
    }

      "2"->{
          println("Quieres iniciar sesion")
          println("Indica tu id: ")
          var idindicado = readLine()!!
          println("Indica tu nombre: ")
          var nombreindicado = readLine()!!
          if (Gimnasio.verficarinicio(idindicado, nombreindicado)) {
              println("Login iniciado $nombreindicado")
              usuarioiniciado = 1
              PersonaActual = usuario(idindicado, nombreindicado, 1)
          } else {
              println("Login fallo")
          }
      }
      //el id para entrar a admin es 1234 y el nombre es Admin
      "3"->{
          println("Quieres iniciar sesion como admin")
          println("Indica el id de admin: ")
          var idadmin = readLine()!!
          println("Indica el nombre admin: ")
          var nombreadmin = readLine()!!
          if (Gimnasio.verficaradmin(idadmin, nombreadmin)) {
              println("admin inicio sesion correcto bienvenido admin")
              usuarioiniciado = 1
              PersonaAdmin = admin(idadmin, nombreadmin, 2)
          } else {
              println("Login admin mal algo")
          }
  }
}

}

    while(true){
        println("Bienvenido al gimnasio de Igor que quieres hacer ahora(usuarios)")
        println("Pulsa 1 para hacer una lista de ejercicios(usuarios)")
        println("Pulsa 2 para ver la lista de ejercicios(usuarios)")
        println("Pulsa 3 para eliminar lista de ejercicios(usuarios)")
        println("Pulsa 4 para hacer una lista de dieta(usuarios)")
        println("Pulsa 5 para ver la lista de alimentos(usuarios)")
        println("Pulsa 6 para eliminar la lista de alimentos(usuarios)")
        println("Pulsa 7 para eliminar usuarios (accion solo disponible solo para admins)")
        println("Pulsa 8 para agregar nuevos usuarios(aaccion disponible solo para admins)")
        println("Pulsa 9 para salir de la aplicacion")
        print("Pulsa el numero que quieras: ")

        when (readLine()?.trim()) {
            "1"->{
                if(PersonaActual != null){
                    PersonaActual.agregarListaEjercicios()
                } else {
                    println("No estas logueado")
                }
            }
            "2"->{
                if (PersonaActual != null){
                    PersonaActual.verlistaejercicios()
                }else{
                    println("No estas logueado")
                }
            }
            "3"->{
                if (PersonaActual != null){
                PersonaActual.eliminarListaEjercicios()
                }
                else{
                    println("No estas logueado")
                }
            }
            "4"->{
                if (PersonaActual != null){
                    PersonaActual.agregarListaDieta()
                }
                else{
                    println("No estas logueado")
                }

            }
            "5"->{
                if (PersonaActual != null){
                    PersonaActual.verListaDieta()
                }else{
                    println("No estas logueado")
                }

            }
            "6"->{
                if (PersonaActual != null){
                    PersonaActual.eliminarListaDieta()
                }else{
                    println("No estas logueado")
                }
            }
            "7"->{
                if (PersonaAdmin?.rol == 2){
                    println("Bienvenido admin a quien quieres eliminar")
                    println("Lista de usuarios: ")
                    Gimnasio.usuarioslista.forEach {
                        println("id: ${it.id} nombre: ${it.nombre}")
                    }
                    PersonaAdmin.eliminarusuarios(Gimnasio.usuarioslista)
                }else{
                    println("No estas logueado")
                }
            }
            "8"->{
                if (PersonaAdmin?.rol == 2){
                    println("Bienvenido al creador de usuarios para admins")
                    println("Lista de usuarios: ")
                    Gimnasio.usuarioslista.forEach {
                        println("id: ${it.id} nombre: ${it.nombre}")
                    }
                    PersonaAdmin.crearNuevosUsuarios(Gimnasio)
                }else{
                    println("No estas logueado")
                }
            }
            "9"->{
                println("Adios gracias por visitar mi gimnasio")
                break
            }
        }
    }}