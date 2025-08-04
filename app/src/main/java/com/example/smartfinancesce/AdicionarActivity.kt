package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartfinancesce.data.Expense
import com.example.smartfinancesce.data.ExpenseDatabase

class AdicionarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        val btnAdicionar = findViewById<Button>(R.id.btnAdicionar)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarHome)
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etValor = findViewById<EditText>(R.id.etValor)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)

        btnAdicionar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val valor = etValor.text.toString().toFloatOrNull()
            val categoria = etCategoria.text.toString()
            if (titulo.isNotBlank() && valor != null && categoria.isNotBlank()) {
                Thread {
                    ExpenseDatabase.getInstance(this).expenseDao()
                        .insert(Expense(title = titulo, amount = valor, category = categoria))
                }.start()
                Toast.makeText(this, "Despesa adicionada!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
