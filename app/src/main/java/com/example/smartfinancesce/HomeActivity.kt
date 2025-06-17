package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ivAdicionar = findViewById<ImageView>(R.id.ivAdicionar)
        val ivOrcamento = findViewById<ImageView>(R.id.ivOrcamento)
        val ivConta = findViewById<ImageView>(R.id.ivConta)
        val ivInicio = findViewById<ImageView>(R.id.ivInicio)

        ivInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java)) // você já está na Home
        }

        ivAdicionar.setOnClickListener {
            startActivity(Intent(this, AdicionarActivity::class.java))
        }

        ivOrcamento.setOnClickListener {
            startActivity(Intent(this, OrcamentoActivity::class.java))
        }

        ivConta.setOnClickListener {
            startActivity(Intent(this, ContaActivity::class.java))
        }
    }
}
