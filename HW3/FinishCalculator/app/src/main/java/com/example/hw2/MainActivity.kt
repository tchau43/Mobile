package com.example.hw2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    private var currentNumber: String = ""
    private var operator: String = ""
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var lastInputWasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.textView)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)

        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnMul).setOnClickListener(this)
        findViewById<Button>(R.id.btnDiv).setOnClickListener(this)

        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
        findViewById<Button>(R.id.btnC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)
        findViewById<Button>(R.id.btnBS).setOnClickListener(this)
        findViewById<Button>(R.id.btnDot).setOnClickListener(this)
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id

        when (id) {
            // Number buttons
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 -> {
                if (lastInputWasOperator) {
                    currentNumber = ""
                    lastInputWasOperator = false
                }
                currentNumber += (v as Button).text.toString()
                textResult.text = currentNumber
            }

            // Dot button for decimals
            R.id.btnDot -> {
                if (!currentNumber.contains(".")) {
                    currentNumber += "."
                    textResult.text = currentNumber
                }
            }

            // Operator buttons
            R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv -> {
                if (currentNumber.isNotEmpty()) {
                    operand1 = currentNumber.toDouble()
                    operator = (v as Button).text.toString()
                    lastInputWasOperator = true
                }
            }

            // Equal button
            R.id.btnEqual -> {
                if (currentNumber.isNotEmpty() && operator.isNotEmpty()) {
                    operand2 = currentNumber.toDouble()
                    val result = calculateResult()
                    textResult.text = result.toString()
                    currentNumber = result.toString()
                    operator = ""
                    lastInputWasOperator = true
                }
            }

            // Clear buttons
            R.id.btnC -> {
                currentNumber = ""
                operator = ""
                operand1 = 0.0
                operand2 = 0.0
                textResult.text = "0"
            }

            R.id.btnCE -> {
                currentNumber = ""
                textResult.text = "0"
            }

            // Backspace button
            R.id.btnBS -> {
                if (currentNumber.isNotEmpty()) {
                    currentNumber = currentNumber.dropLast(1)
                    textResult.text = if (currentNumber.isEmpty()) "0" else currentNumber
                }
            }

            // Plus/Minus button
            R.id.btnPlusMinus -> {
                if (currentNumber.isNotEmpty()) {
                    currentNumber = if (currentNumber.startsWith("-")) {
                        currentNumber.drop(1)
                    } else {
                        "-$currentNumber"
                    }
                    textResult.text = currentNumber
                }
            }
        }
    }

    private fun calculateResult(): Double {
        return when (operator) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "x" -> operand1 * operand2
            "/" -> {
                if (operand2 != 0.0) operand1 / operand2 else Double.NaN
            }
            else -> 0.0
        }
    }
}
