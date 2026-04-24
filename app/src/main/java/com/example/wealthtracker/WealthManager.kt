package com.example.wealthtracker

import kotlin.math.round

class WealthManager(
    private val nameLength: Int,
    private val surnameLength: Int,
    private val birthDay: Int
) {
    data class WealthReport(
        val income: Double,
        val expenses: Double,
        val netIncome: Double,
        val kCoefficient: Double,
        val finalSavings: Double
    )

    fun calculateK(): Double {
        val raw = (nameLength + surnameLength).toDouble() / birthDay.toDouble()
        return raw.roundTo(3)
    }

    fun calculateNetIncome(income: Double, expenses: Double): Double {
        return income - expenses
    }

    fun buildReport(income: Double, expenses: Double): WealthReport {
        val k = calculateK()
        val net = calculateNetIncome(income, expenses)
        val final = net * k
        return WealthReport(
            income = income,
            expenses = expenses,
            netIncome = net,
            kCoefficient = k,
            finalSavings = final
        )
    }

    private fun Double.roundTo(decimals: Int): Double {
        val multiplier = 10.0.powSafe(decimals)
        return round(this * multiplier) / multiplier
    }

    private fun Double.powSafe(power: Int): Double {
        var result = 1.0
        repeat(power) { result *= this }
        return result
    }
}
