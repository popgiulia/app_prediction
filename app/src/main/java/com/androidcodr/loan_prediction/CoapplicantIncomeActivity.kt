package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class CoapplicantIncomeActivity : AppCompatActivity() {

    private val exchangeRate = 4.98
    private val maxEuroValue = 100000
    private val maxRonValue = (maxEuroValue * exchangeRate).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coapplicant_income)

        val seekBarIncome: SeekBar = findViewById(R.id.seekBarCoapplicantIncome)
        val textViewSelectedIncome: TextView = findViewById(R.id.textViewSelectedCoapplicantIncome)
        val radioGroupCurrency: RadioGroup = findViewById(R.id.radioGroupCurrency)
        val btnNext: Button = findViewById(R.id.btnNext)
        val btnBack: Button = findViewById(R.id.btnBack)

        seekBarIncome.max = maxRonValue / 100
        seekBarIncome.progress = 0

        seekBarIncome.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateSelectedIncomeText(radioGroupCurrency.checkedRadioButtonId, progress * 100)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        radioGroupCurrency.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioButtonRON) {
                seekBarIncome.max = maxRonValue / 100
            } else {
                seekBarIncome.max = maxEuroValue / 100
            }
            seekBarIncome.progress = 0
            updateSelectedIncomeText(checkedId, seekBarIncome.progress * 100)
        }

        btnNext.setOnClickListener {
            val selectedIncome = seekBarIncome.progress * 100
            val selectedIncomeInEuro = if (radioGroupCurrency.checkedRadioButtonId == R.id.radioButtonRON) {
                (selectedIncome / exchangeRate).roundToInt()
            } else {
                selectedIncome
            }

            val intent = Intent(this, LoanAmountActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            intent.putExtra("CoapplicantIncome", selectedIncomeInEuro)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun updateSelectedIncomeText(currencyId: Int, progress: Int) {
        val income = progress
        val incomeInEuro = if (currencyId == R.id.radioButtonRON) {
            (income / exchangeRate).roundToInt()
        } else {
            income
        }
        val currency = if (currencyId == R.id.radioButtonRON) "RON" else "Euro"
        val displayText = "Selected Income ($currency): $income \nIncome in Euro: $incomeInEuro"
        findViewById<TextView>(R.id.textViewSelectedCoapplicantIncome).text = displayText
    }
}
