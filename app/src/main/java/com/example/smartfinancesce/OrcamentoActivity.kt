package com.example.smartfinancesce

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.BudgetItem
import org.json.JSONArray
import org.json.JSONObject
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfinancesce.data.BudgetItem

class OrcamentoActivity : AppCompatActivity() {

    private var totalBudget: Double = 0.0
    private val budgetItems = mutableListOf<BudgetItem>()
    private lateinit var adapter: BudgetAdapter
    private lateinit var tvTotalBudget: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvPercent: TextView
    private lateinit var prefs: SharedPreferences

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

        prefs = getSharedPreferences("budget_prefs", MODE_PRIVATE)

        adapter = BudgetAdapter()
        findViewById<RecyclerView>(R.id.rvBudgets).apply {
            layoutManager = LinearLayoutManager(this@OrcamentoActivity)
            adapter = this@OrcamentoActivity.adapter
            isNestedScrollingEnabled = false
        }
        adapter = BudgetAdapter()
        findViewById<RecyclerView>(R.id.rvBudgets).adapter = adapter

        findViewById<Button>(R.id.btnAddTotalBudget).setOnClickListener { showAddTotalDialog() }
        findViewById<Button>(R.id.btnAddBudgetType).setOnClickListener { showAddItemDialog() }

        loadData()
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
                saveData()

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
                    saveData()

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

    private fun saveData() {
        val itemsArray = JSONArray()
        for (item in budgetItems) {
            val obj = JSONObject()
            obj.put("name", item.name)
            obj.put("amount", item.amount)
            itemsArray.put(obj)
        }
        prefs.edit()
            .putString("items", itemsArray.toString())
            .putFloat("total", totalBudget.toFloat())
            .apply()
    }

    private fun loadData() {
        totalBudget = prefs.getFloat("total", 0f).toDouble()
        val itemsString = prefs.getString("items", null)
        if (!itemsString.isNullOrEmpty()) {
            val array = JSONArray(itemsString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                budgetItems.add(BudgetItem(obj.getString("name"), obj.getDouble("amount")))
            }
            adapter.submitList(budgetItems.toList())
        }
        updateProgress()

    }
}

