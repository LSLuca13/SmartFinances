package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AdicionarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarHome)

        btnAdicionar.setOnClickListener {

            Toast.makeText(this, "Despesa adicionada!", Toast.LENGTH_SHORT).show()
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
