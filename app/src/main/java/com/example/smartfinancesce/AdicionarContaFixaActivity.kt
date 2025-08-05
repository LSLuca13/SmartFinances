package com.example.smartfinancesce

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartfinancesce.data.FixedAccount
import com.example.smartfinancesce.data.FixedAccountDatabase

class AdicionarContaFixaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_conta_fixa)

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val etNome = findViewById<EditText>(R.id.etNome)
        val etValor = findViewById<EditText>(R.id.etValor)
        val etData = findViewById<EditText>(R.id.etDataPagamento)

        btnAdicionar.setOnClickListener {
            val nome = etNome.text.toString()
            val valor = etValor.text.toString().toFloatOrNull()
            val data = etData.text.toString()

            if (nome.isNotBlank() && valor != null && data.isNotBlank()) {
                Thread {
                    FixedAccountDatabase.getInstance(this).fixedAccountDao()
                        .insert(
                            FixedAccount(
                                name = nome,
                                amount = valor,
                                paymentDate = data
                            )
                        )
                }.start()
                Toast.makeText(this, "Conta fixa adicionada!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }
}
