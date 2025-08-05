package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NotificacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Ação do botão de voltar
        val ivVoltarConta = findViewById<ImageView>(R.id.ivVoltarConta)
        ivVoltarConta.setOnClickListener {
            val intent = Intent(this, ContaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
