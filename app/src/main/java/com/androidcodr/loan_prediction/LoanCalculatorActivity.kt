package com.androidcodr.loan_prediction

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

class LoanCalculatorActivity : AppCompatActivity() {

    // UI Elements
    private lateinit var rgCurrency: RadioGroup
    private lateinit var rbRON: RadioButton
    private lateinit var rbEUR: RadioButton
    private lateinit var etLoanAmount: EditText
    private lateinit var sbInterestRate: SeekBar
    private lateinit var etInterestRate: EditText
    private lateinit var etLoanTerm: EditText
    private lateinit var spLoanType: Spinner
    private lateinit var tvOriginationDate: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnGenerateSchedule: Button
    private lateinit var tvMonthlyRepayment: TextView
    private lateinit var tvTotalRepayment: TextView
    private lateinit var tvTotalInterest: TextView
    private lateinit var btnBack: Button

    private var isCalculationDone = false // Flag to track if calculation is done

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_calculator)

        // Initialize UI elements
        rgCurrency = findViewById(R.id.rgCurrency)
        rbRON = findViewById(R.id.rbRON)
        rbEUR = findViewById(R.id.rbEUR)
        etLoanAmount = findViewById(R.id.etLoanAmount)
        sbInterestRate = findViewById(R.id.sbInterestRate)
        etInterestRate = findViewById(R.id.etInterestRate)
        etLoanTerm = findViewById(R.id.etLoanTerm)
        spLoanType = findViewById(R.id.spLoanType)
        tvOriginationDate = findViewById(R.id.tvOriginationDate)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnGenerateSchedule = findViewById(R.id.btnGenerateSchedule)
        tvMonthlyRepayment = findViewById(R.id.tvMonthlyRepayment)
        tvTotalRepayment = findViewById(R.id.tvTotalRepayment)
        tvTotalInterest = findViewById(R.id.tvTotalInterest)
        btnBack = findViewById(R.id.btnBack)

        // Setup SeekBar for interest rate
        sbInterestRate.max = 10
        sbInterestRate.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val interestRate = 5 + progress * 0.5
                etInterestRate.setText(interestRate.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Setup date picker for origination date
        tvOriginationDate.setOnClickListener {
            showDatePickerDialog()
        }

        // Setup button click listeners
        btnCalculate.setOnClickListener {
            calculateLoan()
        }

        btnGenerateSchedule.setOnClickListener {
            if (isCalculationDone) {
                generateAmortizationSchedule()
            } else {
                Toast.makeText(this, "Please press Calculate first", Toast.LENGTH_SHORT).show()
            }
        }
        btnBack.setOnClickListener {
            finish()
        }
    }

    // Show date picker dialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvOriginationDate.text = date
            }, year, month, day)

        datePickerDialog.show()
    }

    // Calculation for loan repayments
    private var totalRepaymentVariable = 0.0
    private fun calculateLoan() {
        val loanAmount = etLoanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = etLoanTerm.text.toString().toIntOrNull() ?: 0
        val interestRate = etInterestRate.text.toString().toDoubleOrNull() ?: 0.0
        val loanType = spLoanType.selectedItem.toString()

        when (loanType) {
            "Fixed Rate" -> {
                val monthlyRepayment = calculateRateMonthlyRepayment(loanAmount, loanTerm, interestRate)
                val totalRepayment = monthlyRepayment * loanTerm
                val totalInterest = totalRepayment - loanAmount

                tvMonthlyRepayment.text = "Monthly Repayment: ${String.format("%.2f", monthlyRepayment)}${getCurrencySymbol()}"
                tvTotalRepayment.text = "Total Repayment: ${String.format("%.2f", totalRepayment)}${getCurrencySymbol()}"
                tvTotalInterest.text = "Total Interest: ${String.format("%.2f", totalInterest)}${getCurrencySymbol()}"
            }
            "Variable Rate" -> {
                var annualInterestRate = interestRate
                var monthlyRepayment = 0.0
                for (i in 1..loanTerm) {
                    monthlyRepayment = calculateRateMonthlyRepayment(loanAmount, loanTerm, annualInterestRate)
                    totalRepaymentVariable += monthlyRepayment
                    if (i % 12 == 0 && annualInterestRate > 5) {
                        annualInterestRate -= 0.3
                        if (annualInterestRate < 5) annualInterestRate = 5.0
                    }
                }
                val totalInterest = totalRepaymentVariable - loanAmount

                tvMonthlyRepayment.text = "First Month Repayment: ${String.format("%.2f", monthlyRepayment)}${getCurrencySymbol()}"
                tvTotalRepayment.text = "Total Repayment: ${String.format("%.2f", totalRepaymentVariable)}${getCurrencySymbol()}"
                tvTotalInterest.text = "Total Interest: ${String.format("%.2f", totalInterest)}${getCurrencySymbol()}"
            }
        }

        // Set the flag to true indicating that calculation is done
        isCalculationDone = true
    }

    // Generate amortization schedule
    private fun generateAmortizationSchedule() {
        val loanAmount = etLoanAmount.text.toString().toDoubleOrNull() ?: 0.0
        val loanTerm = etLoanTerm.text.toString().toIntOrNull() ?: 0
        val interestRate = etInterestRate.text.toString().toDoubleOrNull() ?: 0.0
        val loanType = spLoanType.selectedItem.toString()
        val dateParts = tvOriginationDate.text.split("/")
        val day = dateParts[0].toInt()
        val month = dateParts[1].toInt() - 1
        val year = dateParts[2].toInt()

        val originationDate = Calendar.getInstance()
        originationDate.set(year, month, day)

        val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

        val amortizationSchedule = ArrayList<Array<String>>()
        amortizationSchedule.add(arrayOf("Payment #", "Date", "Rate", "Payment", "Balance"))

        var currentDate = originationDate.clone() as Calendar

        when (loanType) {
            "Fixed Rate" -> {
                val monthlyRepayment = calculateRateMonthlyRepayment(loanAmount, loanTerm, interestRate)
                val totalRepayment = monthlyRepayment * loanTerm
                var remainingPayment = totalRepayment
                for (i in 1..loanTerm) {
                    remainingPayment -= monthlyRepayment

                    amortizationSchedule.add(arrayOf(
                        i.toString(),
                        dateFormat.format(currentDate.time),
                        String.format("%.2f", interestRate) + "%",
                        String.format("%.2f", monthlyRepayment),
                        String.format("%.2f", remainingPayment)
                    ))
                    currentDate.add(Calendar.MONTH, 1)
                }
            }
            "Variable Rate" -> {
                var annualInterestRate = interestRate
                var monthlyRepayment = 0.0
                var remainingPayment = totalRepaymentVariable
                for (i in 1..loanTerm) {
                    monthlyRepayment = calculateRateMonthlyRepayment(loanAmount, loanTerm, annualInterestRate)
                    remainingPayment -= monthlyRepayment
                    amortizationSchedule.add(arrayOf(
                        i.toString(),
                        dateFormat.format(currentDate.time),
                        String.format("%.2f", annualInterestRate) + "%",
                        String.format("%.2f", monthlyRepayment),
                        String.format("%.2f", remainingPayment)
                    ))
                    currentDate.add(Calendar.MONTH, 1)

                    // interest rate changes annually
                    if (i % 12 == 0 && annualInterestRate > 5) {
                        annualInterestRate -= 0.3
                        if (annualInterestRate < 5) annualInterestRate = 5.0
                    }
                }
            }
        }

        val intent = Intent(this, AmortizationScheduleActivity::class.java)
        intent.putExtra("amortization_schedule", amortizationSchedule)
        startActivity(intent)
    }

    // Calculate monthly repayment for a given loan amount, term, and interest rate
    private fun calculateRateMonthlyRepayment(loanAmount: Double, loanTerm: Int, interestRate: Double): Double {
        val monthlyInterestRate = interestRate / 100 / 12
        return loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -loanTerm.toDouble()))
    }

    // Get the currency symbol based on the selected radio button
    private fun getCurrencySymbol(): String {
        return when (rgCurrency.checkedRadioButtonId) {
            R.id.rbRON -> "RON "
            R.id.rbEUR -> "EUR "
            else -> "$"
        }
    }
}
