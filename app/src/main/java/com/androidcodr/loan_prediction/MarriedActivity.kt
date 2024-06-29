package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MarriedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_married)

        val radioGroupMarried = findViewById<RadioGroup>(R.id.radioGroupMarried)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupMarried.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@MarriedActivity, EducationActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioYes -> intent.putExtra("Married", 0)
                R.id.radioNo -> intent.putExtra("Married", 1)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupMarried.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@MarriedActivity, EducationActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioYes -> intent.putExtra("Married", 1)
                    R.id.radioNo -> intent.putExtra("Married", 0)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
