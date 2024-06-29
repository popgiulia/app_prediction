package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class LoanAmountActivity : AppCompatActivity() {

    private val exchangeRate = 4.98
    private val maxEuroValue = 100
    private val maxRonValue = (maxEuroValue * exchangeRate).roundToInt()
    private val minRonValue = (1 * exchangeRate).roundToInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_amount)

        val seekBarLoanAmount: SeekBar = findViewById(R.id.seekBarLoanAmount)
        val textViewSelectedLoanAmount: TextView = findViewById(R.id.textViewSelectedLoanAmount)
        val radioGroupCurrency: RadioGroup = findViewById(R.id.radioGroupCurrency)
        val btnNext: Button = findViewById(R.id.btnNext)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Initialize the SeekBar for RON
        seekBarLoanAmount.max = maxRonValue - minRonValue
        seekBarLoanAmount.progress = 0

        // Update TextView as SeekBar is moved
        seekBarLoanAmount.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateSelectedLoanAmountText(radioGroupCurrency.checkedRadioButtonId, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Update SeekBar max value and TextView when RadioButton selection changes
        radioGroupCurrency.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioButtonRON) {
                seekBarLoanAmount.max = maxRonValue - minRonValue
                seekBarLoanAmount.progress = 0
            } else {
                seekBarLoanAmount.max = maxEuroValue - 1
                seekBarLoanAmount.progress = 0
            }
            updateSelectedLoanAmountText(checkedId, seekBarLoanAmount.progress)
        }

        btnNext.setOnClickListener {
            val selectedLoanAmount = seekBarLoanAmount.progress + 1
            val selectedLoanAmountInEuro = if (radioGroupCurrency.checkedRadioButtonId == R.id.radioButtonRON) {
                ((selectedLoanAmount + minRonValue) / exchangeRate).roundToInt()
            } else {
                selectedLoanAmount
            }

            val intent = Intent(this, LoanAmountTermActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            intent.putExtra("LoanAmount", selectedLoanAmountInEuro)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateSelectedLoanAmountText(currencyId: Int, progress: Int) {
        val loanAmount = if (currencyId == R.id.radioButtonRON) progress + minRonValue else progress + 1
        val loanAmountInEuro = if (currencyId == R.id.radioButtonRON) {
            (loanAmount / exchangeRate).roundToInt()
        } else {
            loanAmount
        }
        val currency = if (currencyId == R.id.radioButtonRON) "RON" else "Euro"
        val displayText = "Loan Amount ($currency): $loanAmount thousand\nAmount in Euro: $loanAmountInEuro thousand"
        findViewById<TextView>(R.id.textViewSelectedLoanAmount).text = displayText
    }
}
