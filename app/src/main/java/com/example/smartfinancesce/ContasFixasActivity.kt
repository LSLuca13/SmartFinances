package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ContasFixasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contas_fixas)

        val btnProximaTela = findViewById<Button>(R.id.btnProximaTela)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        btnProximaTela.setOnClickListener {
            val intent = Intent(this, AdicionarActivity::class.java)
            startActivity(intent)
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, ContaActivity::class.java)
            startActivity(intent)
        }
    }
}
