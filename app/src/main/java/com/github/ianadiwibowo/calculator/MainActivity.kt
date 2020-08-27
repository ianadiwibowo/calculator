package com.github.ianadiwibowo.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var lastDigit = false
    var lastDot = false

    fun onDigit(view: View) {
        tvInput.append((view as Button).text)
        lastDigit = true
    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastDigit = false
        lastDot = false
    }

    fun onDecimal(view: View) {
        if (lastDigit && !lastDot) {
            tvInput.append(".")
            lastDigit = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastDigit && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastDigit = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        if (value.startsWith("-")) return false

        return value.contains("/") || value.contains("*") ||
                value.contains("-") || value.contains("+")
    }

    fun onEqual(view: View) {
        if (!lastDigit) return

        var tvValue = tvInput.text.toString()

        try {
            var prefix = ""
            if (tvValue.startsWith("-")) {
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            if (tvValue.contains("/")) {
                val splitValues = tvValue.split("/")
                var one = splitValues[0]
                var two = splitValues[1]
                if (!prefix.isEmpty())
                    one = prefix + one
                val result = one.toDouble() / two.toDouble()
                tvInput.text = removeTrailingZero(result.toString())
            } else if (tvValue.contains("*")) {
                val splitValues = tvValue.split("*")
                var one = splitValues[0]
                var two = splitValues[1]
                if (!prefix.isEmpty())
                    one = prefix + one
                val result = one.toDouble() * two.toDouble()
                tvInput.text = removeTrailingZero(result.toString())
            } else if (tvValue.contains("-")) {
                val splitValues = tvValue.split("-")
                var one = splitValues[0]
                var two = splitValues[1]
                if (!prefix.isEmpty())
                    one = prefix + one
                val result = one.toDouble() - two.toDouble()
                tvInput.text = removeTrailingZero(result.toString())
            } else {
                val splitValues = tvValue.split("+")
                var one = splitValues[0]
                var two = splitValues[1]
                if (!prefix.isEmpty())
                    one = prefix + one
                val result = one.toDouble() + two.toDouble()
                tvInput.text = removeTrailingZero(result.toString())
            }
        } catch (e: ArithmeticException) {

        }
    }

    private fun removeTrailingZero(result: String): String {
        return if (result.endsWith(".0"))
            result.substring(0, result.length - 2)
        else
            result
    }
}
