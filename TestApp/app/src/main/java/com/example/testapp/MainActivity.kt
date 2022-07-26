package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val f = foo(9, "6")

        val f2 = foo(brand = "", year = 12, model = "dgoj")
        val f22 = foo(b ="bbb", c=32)

        val sdlkibhfjks = f22.b
        Log.e("Valor", f22.doSomething(20) { n -> (n*3).toString() }) //Le pasamos por parametrso un valor y una función, la función no se mete dentro, sino que se declara después
        //it o n es como un this, en este caso hace referencia al 20, lo multiplica por 3 y lo pasa a String porque hemos declarado que entra int (n=20) y sale String.

    }
}