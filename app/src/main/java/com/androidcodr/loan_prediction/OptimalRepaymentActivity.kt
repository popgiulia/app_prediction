package com.androidcodr.loan_prediction

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class OptimalRepaymentActivity : AppCompatActivity() {

    private lateinit var rgCurrency: RadioGroup
    private lateinit var rbRON: RadioButton
    private lateinit var rbEUR: RadioButton
    private lateinit var etLoanAmount: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var etLoanTerm: EditText
    private lateinit var etInitialSavings: EditText
    private lateinit var etSavingsInterestRate: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvTotalInterest: TextView
    private lateinit var tvMonthlyContribution: TextView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optimal_repayment)

        rgCurrency = findViewById(R.id.rgCurrency)
        rbRON = findViewById(R.id.rbRON)
        rbEUR = findViewById(R.id.rbEUR)
        etLoanAmount = findViewById(R.id.etLoanAmount)
        etInterestRate = findViewById(R.id.etInterestRate)
        etLoanTerm = findViewById(R.id.etLoanTerm)
        etInitialSavings = findViewById(R.id.etInitialSavings)
        etSavingsInterestRate = findViewById(R.id.etSavingsInterestRate)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvTotalInterest = findViewById(R.id.tvTotalInterest)
        tvMonthlyContribution = findViewById(R.id.tvMonthlyContribution)
        btnBack = findViewById(R.id.btnBack)

        btnCalculate.setOnClickListener {
            calculateOptimalRepayment()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun calculateOptimalRepayment() {
        val loanAmount = etLoanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = etInterestRate.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = etLoanTerm.text.toString().toIntOrNull() ?: 0
        val initialSavings = etInitialSavings.text.toString().toDoubleOrNull() ?: 0.0
        val savingsInterestRate = etSavingsInterestRate.text.toString().toDoubleOrNull() ?: 0.0
        val currencySymbol = if (rgCurrency.checkedRadioButtonId == R.id.rbRON) "RON" else "€"

        // Calculăm dobânda lunară a împrumutului
        val monthlyInterestRate = interestRate / 100 / 12
        val M = loanAmount * monthlyInterestRate / (1 - (1 + monthlyInterestRate).pow(-loanTerm))

        // Calculăm dobânda totală a împrumutului
        val totalInterest = M * loanTerm - loanAmount

        // Calculăm contribuția lunară necesară pe întreaga durată a împrumutului
        val monthlySavingsInterestRate = savingsInterestRate / 100 / 12
        val futureValueFactor = (1 + monthlySavingsInterestRate).pow(loanTerm)
        val monthlyContribution = (totalInterest - initialSavings * futureValueFactor) /
                ((futureValueFactor - 1) / monthlySavingsInterestRate)

        tvTotalInterest.text = "Total Interest: ${String.format("%.2f", totalInterest)}$currencySymbol"
        tvMonthlyContribution.text = "Monthly Contribution: ${String.format("%.2f", monthlyContribution)}$currencySymbol"
    }
}
