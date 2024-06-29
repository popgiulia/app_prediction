package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class SavingsActivity : AppCompatActivity() {

    private lateinit var rgCurrency: RadioGroup
    private lateinit var rbRON: RadioButton
    private lateinit var rbEUR: RadioButton
    private lateinit var etInitialAmount: EditText
    private lateinit var etMonthlyDeposit: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var etMonths: EditText
    private lateinit var btnCalculateSavings: Button
    private lateinit var btnBack: Button
    private lateinit var tvGrossEarnings: TextView
    private lateinit var tvWithholdingTax: TextView
    private lateinit var tvNetReturn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        rgCurrency = findViewById(R.id.rgCurrency)
        rbRON = findViewById(R.id.rbRON)
        rbEUR = findViewById(R.id.rbEUR)
        etInitialAmount = findViewById(R.id.etInitialAmount)
        etMonthlyDeposit = findViewById(R.id.etMonthlyDeposit)
        etInterestRate = findViewById(R.id.etInterestRate)
        etMonths = findViewById(R.id.etMonths)
        btnCalculateSavings = findViewById(R.id.btnCalculateSavings)
        btnBack = findViewById(R.id.btnBack)
        tvGrossEarnings = findViewById(R.id.tvGrossEarnings)
        tvWithholdingTax = findViewById(R.id.tvWithholdingTax)
        tvNetReturn = findViewById(R.id.tvNetReturn)

        btnCalculateSavings.setOnClickListener {
            calculateSavings()
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun calculateSavings() {
        val initialAmount = etInitialAmount.text.toString().toDoubleOrNull() ?: 0.0
        val monthlyDeposit = etMonthlyDeposit.text.toString().toDoubleOrNull() ?: 0.0
        val monthlyInterestRate = (etInterestRate.text.toString().toDoubleOrNull() ?: 0.0) / 100 / 12
        val totalMonths = etMonths.text.toString().toIntOrNull() ?: 0
        val currencySymbol = if (rgCurrency.checkedRadioButtonId == R.id.rbRON) "RON" else "€"

        val futureValueInitialAmount = initialAmount * (1 + monthlyInterestRate).pow(totalMonths)
        val futureValueDeposits = monthlyDeposit * ((1 + monthlyInterestRate).pow(totalMonths) - 1) / monthlyInterestRate
        val grossEarnings = futureValueInitialAmount + futureValueDeposits
        val withholdingTax = (grossEarnings - (initialAmount + monthlyDeposit * totalMonths)) * 0.15
        val netReturn = grossEarnings - withholdingTax

        tvGrossEarnings.text = "Gross Earnings: ${String.format("%.2f", grossEarnings)}$currencySymbol"
        tvWithholdingTax.text = "Withholding Tax (15%): ${String.format("%.2f", withholdingTax)}$currencySymbol"
        tvNetReturn.text = "Net Return: ${String.format("%.2f", netReturn)}$currencySymbol"
    }
}
