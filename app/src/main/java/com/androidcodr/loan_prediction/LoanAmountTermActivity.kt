package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class LoanAmountTermActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_amount_term)

        val loanAmountTermSpinner: Spinner = findViewById(R.id.spinnerLoanAmountTerm)
        val btnNext: Button = findViewById(R.id.btnNext)
        val btnBack: Button = findViewById(R.id.btnBack)

        // Creăm o listă de valori de la 12 la 480, cu un pas de 12
        val loanAmountTermValues = (12..480 step 12).toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, loanAmountTermValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        loanAmountTermSpinner.adapter = adapter

        btnNext.setOnClickListener {
            val selectedLoanAmountTerm = loanAmountTermSpinner.selectedItem as Int

            val intent = Intent(this, CreditHistoryActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            intent.putExtra("LoanAmountTerm", selectedLoanAmountTerm)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
