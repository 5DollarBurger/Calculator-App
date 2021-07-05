package com.example.calculator_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.calculator_app.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var lastNumeric : Boolean = true
    var pressedDec : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = true
        pressedDec = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !pressedDec) {
            binding.tvInput.append(".")
            lastNumeric = false
            pressedDec = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = binding.tvInput.text.toString()
            var isNegative : Boolean = false
            try {
                if (tvValue.startsWith("-")) {
                    isNegative = true
                    // Ignore negative for now
                    tvValue = tvValue.substring(1)
                }

                var result : Double = 0.0
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var a = splitValue[0].toDouble()
                    var b = splitValue[1].toDouble()
                    if (isNegative) {a = -a}
                    result = a - b
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var a = splitValue[0].toDouble()
                    var b = splitValue[1].toDouble()
                    if (isNegative) {a = -a}
                    result = a + b
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var a = splitValue[0].toDouble()
                    var b = splitValue[1].toDouble()
                    if (isNegative) {a = -a}
                    result = a / b
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var a = splitValue[0].toDouble()
                    var b = splitValue[1].toDouble()
                    if (isNegative) {a = -a}
                    result = a * b
                }

                binding.tvInput.text = removeZeroAfterDec(result.toString())
            } catch (e: ArithmeticException) {e.printStackTrace()}
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            pressedDec = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDec(result: String) : String {
        var value = result
        if (result.endsWith(".0"))
            value = result.substring(0,result.length-2)
        return value
    }
}