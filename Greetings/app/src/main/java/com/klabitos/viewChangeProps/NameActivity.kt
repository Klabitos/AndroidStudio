package com.klabitos.viewChangeProps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class NameActivity : AppCompatActivity() {

    private lateinit var finalName:TextView
    private lateinit var btnBack:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)


        linkVariables()
        printName()
    }

    private fun linkVariables(){
        finalName = findViewById(R.id.resultName)
        btnBack = findViewById(R.id.btn_goBack)
        btnBack.setOnClickListener { goBack() }
    }

    private fun printName(){
        val bundle = intent.extras
        val name = bundle?.get("name")
        finalName.text = getString(R.string.salute, name)
    }

    private fun goBack(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}