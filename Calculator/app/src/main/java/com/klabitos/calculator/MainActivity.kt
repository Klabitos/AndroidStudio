package com.klabitos.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    /* private var firstInputNumber:EditText?=null  //Si quito la interrogación null es solo EditText, asi puede ser nulo tmabien
                                                    //Luego si no encuentra ese id puede dar null y crashea all, por lo que es mejor dejarlo asi desde el principio
    private var secondInputNumber:EditText?=null
    private var operationSymbol:TextView?=null
    private var resultNumber:TextView?=null */

    lateinit var firstInputNumber:EditText
    lateinit var operationSymbol:TextView
    lateinit var secondInputNumber:EditText
    lateinit var resultNumber:TextView




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 /*El r.id hace referencia a la vista*/
        //loquesea = findViewById(R.id.loquesea)
        //loquesea.text="loquesea"
        firstInputNumber = findViewById(R.id.firstInputNumber)
        secondInputNumber = findViewById(R.id.secondInputNumber)
        operationSymbol = findViewById(R.id.textOperation)
        resultNumber = findViewById(R.id.textResult)

        findViewById<Button>(R.id.buttonSumar)?.setOnClickListener{ setSuma() }
        findViewById<Button>(R.id.buttonMultiplicar)?.setOnClickListener{ setMultiplicacion() }
        findViewById<Button>(R.id.buttonRestar)?.setOnClickListener{ setResta() }

    }

    private fun setSuma(){
        operationSymbol.text="+"
        doOperation()
    }
    private fun setResta(){
        operationSymbol.text="-"
        doOperation()
    }
    private fun setMultiplicacion(){
        operationSymbol.text="x"
        operationSymbol.x
        doOperation()
    }

    private fun doOperation(){
        if(firstInputNumber.text.toString()==""||secondInputNumber.text.toString().toString()=="") return
        val number1 = firstInputNumber.text.toString().toInt()
        val number2 = secondInputNumber.text.toString().toInt()
        when(operationSymbol.text){ //En caso de que sea opera.. y no null, getText, que es igual text. //as int? por si es null, ademas si es null va a lo de ?:return
            "+" -> resultNumber.text = addValues(number1, number2).toString() //!! fuerza que no es null
            "-" -> resultNumber?.text = sustractValues(number1, number2).toString()
            "x" -> resultNumber?.text = multiplyValues(number1, number2).toString()
            null -> {
                //bloque de codigo si es null
            }
        }
    }

    private fun addValues(a:Int, b:Int):Int{
        return a+b
    }
    private fun sustractValues(a:Int, b:Int):Int{
        return a-b
    }
    private fun multiplyValues(a:Int, b:Int):Int{
        return a*b
    }
}