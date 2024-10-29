package com.example.b1_currency

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmount: EditText
    private lateinit var targetAmount: EditText
    private lateinit var sourceCurrency: Spinner
    private lateinit var targetCurrency: Spinner
    private lateinit var exchangeRateText: TextView
    private lateinit var updateRates: TextView

    // Conversion rates for demonstration
    private val conversionRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.93,
        "GBP" to 0.77,
        "JPY" to 153.74,
        "AUD" to 1.53,
        "VND" to 25.295
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        sourceAmount = findViewById(R.id.sourceAmount)
        targetAmount = findViewById(R.id.targetAmount)
        sourceCurrency = findViewById(R.id.sourceCurrency)
        targetCurrency = findViewById(R.id.targetCurrency)
        exchangeRateText = findViewById(R.id.exchangeRate)
        updateRates = findViewById(R.id.updateRates)

        // Set up currency spinners
        val currencyList = listOf("USD", "EUR", "GBP", "JPY", "AUD", "VND")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sourceCurrency.adapter = adapter
        targetCurrency.adapter = adapter

        // Listeners for spinners
        sourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                updateExchangeRateDisplay()
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        targetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                updateExchangeRateDisplay()
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Initialize numeric keypad buttons
        initializeKeypad()

        // Update rate button (simulated)
        updateRates.setOnClickListener {
            Toast.makeText(this, "Rates updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeKeypad() {
        findViewById<Button>(R.id.btn0).setOnClickListener { appendNumber("0") }
        findViewById<Button>(R.id.btn1).setOnClickListener { appendNumber("1") }
        findViewById<Button>(R.id.btn2).setOnClickListener { appendNumber("2") }
        findViewById<Button>(R.id.btn3).setOnClickListener { appendNumber("3") }
        findViewById<Button>(R.id.btn4).setOnClickListener { appendNumber("4") }
        findViewById<Button>(R.id.btn5).setOnClickListener { appendNumber("5") }
        findViewById<Button>(R.id.btn6).setOnClickListener { appendNumber("6") }
        findViewById<Button>(R.id.btn7).setOnClickListener { appendNumber("7") }
        findViewById<Button>(R.id.btn8).setOnClickListener { appendNumber("8") }
        findViewById<Button>(R.id.btn9).setOnClickListener { appendNumber("9") }
        findViewById<Button>(R.id.btnDot).setOnClickListener { appendNumber(".") }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearInput() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }
    }

    private fun appendNumber(number: String) {
        if (number == "." && sourceAmount.text.contains(".")) return
        sourceAmount.append(number)
        convertCurrency()
    }

    private fun clearInput() {
        sourceAmount.text.clear()
        targetAmount.setText("0")
    }

    private fun backspace() {
        val currentText = sourceAmount.text.toString()
        if (currentText.isNotEmpty()) {
            sourceAmount.setText(currentText.dropLast(1))
            sourceAmount.setSelection(sourceAmount.text.length)
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        val amount = sourceAmount.text.toString().toDoubleOrNull() ?: return
        val sourceRate = conversionRates[sourceCurrency.selectedItem.toString()] ?: return
        val targetRate = conversionRates[targetCurrency.selectedItem.toString()] ?: return
        val result = round((amount / sourceRate) * targetRate * 100) / 100
        targetAmount.setText(result.toString())
    }

    private fun updateExchangeRateDisplay() {
        val source = sourceCurrency.selectedItem.toString()
        val target = targetCurrency.selectedItem.toString()
        val sourceRate = conversionRates[source] ?: return
        val targetRate = conversionRates[target] ?: return
        val exchangeRate = round((targetRate / sourceRate) * 10000) / 10000
        exchangeRateText.text = "1 $source = $exchangeRate $target"
    }
}
