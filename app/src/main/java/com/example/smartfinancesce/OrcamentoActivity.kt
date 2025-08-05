package com.example.smartfinancesce

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.BudgetItem

class OrcamentoActivity : AppCompatActivity() {

    private var totalBudget: Double = 0.0
    private val budgetItems = mutableListOf<BudgetItem>()
    private lateinit var adapter: BudgetAdapter
    private lateinit var tvTotalBudget: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvPercent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orcamento)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarHome)
        btnVoltar.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        tvTotalBudget = findViewById(R.id.tvTotalBudget)
        progressBar = findViewById(R.id.pbBudget)
        tvPercent = findViewById(R.id.tvPercent)

        adapter = BudgetAdapter()
        findViewById<RecyclerView>(R.id.rvBudgets).adapter = adapter

        findViewById<Button>(R.id.btnAddTotalBudget).setOnClickListener { showAddTotalDialog() }
        findViewById<Button>(R.id.btnAddBudgetType).setOnClickListener { showAddItemDialog() }

        updateProgress()
    }

    private fun showAddTotalDialog() {
        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        AlertDialog.Builder(this)
            .setTitle("Orçamento Total")
            .setView(input)
            .setPositiveButton("Salvar") { _, _ ->
                totalBudget = input.text.toString().toDoubleOrNull() ?: 0.0
                updateProgress()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showAddItemDialog() {
        if (totalBudget <= 0) {
            Toast.makeText(this, "Defina o orçamento total primeiro", Toast.LENGTH_SHORT).show()
            return
        }

        val nameInput = EditText(this).apply { hint = "Nome" }
        val valueInput = EditText(this).apply {
            hint = "Valor"
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(nameInput)
            addView(valueInput)
            setPadding(50, 40, 50, 10)
        }

        AlertDialog.Builder(this)
            .setTitle("Adicionar tipo de orçamento")
            .setView(layout)
            .setPositiveButton("Adicionar") { _, _ ->
                val name = nameInput.text.toString()
                val value = valueInput.text.toString().toDoubleOrNull() ?: 0.0
                val used = budgetItems.sumOf { it.amount }
                if (name.isBlank() || value <= 0) return@setPositiveButton
                if (used + value > totalBudget) {
                    Toast.makeText(this, "Valor excede o orçamento total", Toast.LENGTH_SHORT).show()
                } else {
                    budgetItems.add(BudgetItem(name, value))
                    adapter.submitList(budgetItems.toList())
                    updateProgress()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun updateProgress() {
        val used = budgetItems.sumOf { it.amount }
        val percent = if (totalBudget > 0) ((used / totalBudget) * 100).toInt() else 0
        progressBar.progress = percent.coerceAtMost(100)
        tvPercent.text = "$percent% do orçamento"
        tvTotalBudget.text = String.format("Total: R$ %.2f", totalBudget)
    }
}

