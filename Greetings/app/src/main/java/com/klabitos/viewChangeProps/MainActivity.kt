package com.klabitos.viewChangeProps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var inputName: EditText
    private lateinit var btnSay:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linkElements()
    }

    private fun linkElements(){
        inputName = findViewById(R.id.inputName)
        btnSay = findViewById(R.id.btnName)
        btnSay.setOnClickListener { checkValue() }
    }

    private fun checkValue(){
        if(inputName.text.toString().isEmpty()){
            Toast.makeText(this, "SAY MY NAME", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this, NameActivity::class.java)
            intent.putExtra("name", inputName.text.toString())
            startActivity(intent)
        }
    }
}