package com.example.smartfinancesce

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ivAdicionar = findViewById<ImageView>(R.id.ivAdicionar)
        val ivOrcamento = findViewById<ImageView>(R.id.ivOrcamento)
        val ivConta = findViewById<ImageView>(R.id.ivConta)
        val ivInicio = findViewById<ImageView>(R.id.ivInicio)
        val pieChart = findViewById<PieChart>(R.id.pieChart)
        val sampleData = mapOf(
            "Alimentação" to 40f,
            "Transporte" to 30f,
            "Outros" to 30f
        )
        setupPieChart(pieChart, sampleData)

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
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS.toList())
        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.invalidate()
    }
}
