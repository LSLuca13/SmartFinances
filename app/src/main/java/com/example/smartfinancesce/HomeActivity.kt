package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.ExpenseDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeActivity : AppCompatActivity() {
    private val categoryColors = mutableMapOf<String, Int>()

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
        val adapter = ExpenseAdapter(::colorForCategory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val dao = ExpenseDatabase.getInstance(this).expenseDao()
        dao.getAll().observe(this) { expenses ->
            adapter.submitList(expenses)
            val totals = expenses.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount.toDouble() }.toFloat() }
            setupPieChart(pieChart, totals)
        }
=======
        val entries = listOf(
            PieEntry(40f, "Alimentação"),
            PieEntry(30f, "Transporte"),
            PieEntry(30f, "Outros")
        )
        val dataSet = PieDataSet(entries, "")
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS.toList())
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.invalidate()

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

    private fun setupPieChart(pieChart: PieChart, values: Map<String, Float>) {
        val entries = values.map { PieEntry(it.value, it.key) }
        val dataSet = PieDataSet(entries, "")
        val colors = entries.map { colorForCategory(it.label) }
        dataSet.colors = colors
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()
    }
}
