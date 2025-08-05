package com.example.smartfinancesce

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.ExpenseDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import android.graphics.Color

class HomeActivity : AppCompatActivity() {

    private val categoryColors = mutableMapOf<String, Int>()
    private var balance = 0f
    private var expensesTotal = 0f
    private lateinit var tvValorTotal: TextView

    private fun colorForCategory(category: String): Int {
        return categoryColors.getOrPut(category) {
            val index = categoryColors.size % ColorTemplate.MATERIAL_COLORS.size
            ColorTemplate.MATERIAL_COLORS[index]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ivAdicionar = findViewById<ImageView>(R.id.ivAdicionar)
        val ivOrcamento = findViewById<ImageView>(R.id.ivOrcamento)
        val ivConta = findViewById<ImageView>(R.id.ivConta)
        val ivInicio = findViewById<ImageView>(R.id.ivInicio)
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val recyclerView = findViewById<RecyclerView>(R.id.rvExpenses)
        val btnSaldo = findViewById<Button>(R.id.btnAdicionarSaldo)
        tvValorTotal = findViewById(R.id.valorTotal)

        balance = getSharedPreferences("finance_prefs", MODE_PRIVATE).getFloat("balance", 0f)

        val adapter = ExpenseAdapter(::colorForCategory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val dao = ExpenseDatabase.getInstance(this).expenseDao()
        dao.getAll().observe(this) { expenses ->
            adapter.submitList(expenses)

            val totals = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount.toDouble() }.toFloat() }

            setupPieChart(pieChart, totals)

            expensesTotal = expenses.sumOf { it.amount.toDouble() }.toFloat()
            updateBalanceDisplay()
        }

        btnSaldo.setOnClickListener {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            AlertDialog.Builder(this)
                .setTitle("Adicionar Saldo")
                .setView(input)
                .setPositiveButton("Adicionar") { _, _ ->
                    val value = input.text.toString().toFloatOrNull()
                    if (value != null) {
                        balance += value
                        saveBalance()
                        updateBalanceDisplay()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        ivInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java)) // já está na Home
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

    private fun setupPieChart(pieChart: PieChart, values: Map<String, Float>) {
        val entries = values.map { PieEntry(it.value, it.key) }
        val dataSet = PieDataSet(entries, "")

        // Cores para cada categoria
        val colors = entries.map { colorForCategory(it.label) }
        dataSet.colors = colors

        val data = PieData(dataSet)

        data.setValueTextSize(16f)
        data.setValueTextColor(Color.BLACK)

        pieChart.setEntryLabelTextSize(14f)
        pieChart.setEntryLabelColor(Color.BLACK)

        val legend = pieChart.legend
        legend.textSize = 16f
        legend.textColor = Color.BLACK
        legend.isWordWrapEnabled = true

        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()
    }


    private fun updateBalanceDisplay() {
        tvValorTotal.text = String.format("R$ %.2f", balance - expensesTotal)
    }

    private fun saveBalance() {
        getSharedPreferences("finance_prefs", MODE_PRIVATE)
            .edit().putFloat("balance", balance).apply()
    }
}
