package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)

        val tvContasFixas = findViewById<TextView>(R.id.tvContasFixas)
        val tvNotificacoes = findViewById<TextView>(R.id.tvNotificacoes)
        val tvSair = findViewById<TextView>(R.id.tvSair)


        tvContasFixas.setOnClickListener {
            startActivity(Intent(this, ContasFixasActivity::class.java))
        }

        tvNotificacoes.setOnClickListener {
            startActivity(Intent(this, NotificacaoActivity::class.java))
        }

        tvSair.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarHome)
        btnVoltar.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }
}
