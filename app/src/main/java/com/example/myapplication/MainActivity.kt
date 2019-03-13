package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.sin
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    var numButtons = arrayListOf<Button>()

    var isShowingResult = false
    var pressedEquals = false
    var ans = 0.0
    lateinit var lastOperation: (a: Double, b: Double) -> Double

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numButtons = arrayListOf(
            zeroButton,
            oneButton,
            twoButton,
            threeButton,
            fourButton,
            fiveButton,
            sixButton,
            sevenButton,
            eightButton,
            nineButton,
            dotButton
        )
        numButtons.forEach { n ->
            n.setOnClickListener {
                pressedEquals = false
                if (!isShowingResult) {
                    val text = resultTextView.text.toString()
                    when (n.text.toString()) {
                        "0" -> {
                            if (text != "0") {
                                resultTextView.append(n.text)
                            }
                        }
                        "." -> {
                            if (!text.contains("."))
                                resultTextView.append(n.text)
                        }
                        else -> {
                            if (text == "0") {
                                resultTextView.text = n.text
                            } else {
                                resultTextView.append(n.text)
                            }
                        }
                    }
                } else {
                    resultTextView.text = n.text
                    isShowingResult = false
                }
            }
        }
        addButton.setOnClickListener {
            prepareOperation { a, b -> a + b }
        }

        subButton.setOnClickListener {
            prepareOperation { a, b -> a - b }
        }

        multButton.setOnClickListener {
            prepareOperation { a, b -> a * b }
        }

        divButton.setOnClickListener {
            prepareOperation { a, b -> a / b }
        }

        equalsButton.setOnClickListener {
            runOperation()
        }

        sqButton.setOnClickListener {
            runOperation { it * it }
        }
        sqrtButton.setOnClickListener {
            runOperation { sqrt(it) }
        }

        eButton.setOnClickListener {
            runOperation { Math.exp(it) }
        }

        lnButton.setOnClickListener {
            runOperation { ln(it) }
        }

        factButton.setOnClickListener {
            runOperation { it }
        }

        tgButton.setOnClickListener {
            runOperation { Math.tan(it) }
        }

        sinButton.setOnClickListener {
            runOperation { sin(it) }
        }

        cosButton.setOnClickListener {
            runOperation { cos(it) }
        }

        cButton.setOnClickListener {
            resultTextView.text = ""
        }
        ceButton.setOnClickListener {
            ans = 0.0
            lastOperation = { _, _ -> 0.0 }
            resultTextView.text = ""
            isShowingResult = false
        }
    }

    private fun runOperation(operation: (x: Double) -> Double) {
        try {
            val number = resultTextView.text.toString().toDouble()
            ans = operation(number)
            resultTextView.text = if (ans.isFinite()) {
                isShowingResult = true
                ans.toString()
            } else {
                "Errore"
            }
        } catch (e: Exception) {
            Toast.makeText(this.baseContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun runOperation() {
        try {
            val text = resultTextView.text.toString()
            if (!text.isEmpty()) {
                val number = text.toDouble()
                val result = if (!pressedEquals) {
                    pressedEquals = true
                    lastOperation(ans, number).also { ans = number }
                } else {
                    lastOperation(number, ans)
                }

                resultTextView.text = if (result.isFinite()) {
                    isShowingResult = true
                    result.toString()
                } else {
                    "Errore"
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this.baseContext, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun prepareOperation(operation: (a: Double, b: Double) -> Double) {
        ans = resultTextView.text.toString().toDouble()
        lastOperation = operation
        resultTextView.text = ""
    }
}