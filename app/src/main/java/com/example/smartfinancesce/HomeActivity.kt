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
}
