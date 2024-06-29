package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class CreditHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_history)

        val radioGroupCreditHistory = findViewById<RadioGroup>(R.id.radioGroupCreditHistory)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupCreditHistory.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@CreditHistoryActivity, PredictionActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioYes -> intent.putExtra("CreditHistory", 1)
                R.id.radioNo -> intent.putExtra("CreditHistory", 0)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupCreditHistory.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@CreditHistoryActivity, PredictionActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioYes -> intent.putExtra("CreditHistory", 1)
                    R.id.radioNo -> intent.putExtra("CreditHistory", 0)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
