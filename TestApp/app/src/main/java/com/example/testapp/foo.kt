package com.example.testapp

class foo(var brand: String = "", var model: String, var year: Int) { //Constructor para instanciar clase

    var b : Int = 9
    get(){
        return field * 3 //field hace referencia al valor d elo que esta arriba (b). Se hace inmediatamente debajo de la declaracion, y es sobreescribir el getter que tiene por defecto.
    }

    var c = 4
    get() = field * 3 //Es otra manera de indicar el return, con = en una sola linea


    init {  //Nada mas instanciarse

    }

    constructor(c : Int): this(c.toString(), c.toString(), 2000) //otra manera de constructor, los : indican que decuelve una instancia de esta misma clase con los dos parametros.
    constructor(c : Int, b:String) : this(c.toString(), "Clase c", 2000)

    fun doSomething(value : Int, f : (Int)->(String)) : String = f.invoke(value)
    //Es una función en la que el primer parametro es un int, y el segundo una función
    //Lo que hacce es invocar f, la función del parametro con el valor que hemos incluido

}


