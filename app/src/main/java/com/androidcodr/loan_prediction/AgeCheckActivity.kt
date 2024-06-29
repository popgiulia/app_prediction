package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AgeCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_check)

        val radioGroupAge: RadioGroup = findViewById(R.id.radioGroupAge)
        val btnNext: Button = findViewById(R.id.btnNext)
        val btnBack: Button = findViewById(R.id.btnBack)

        radioGroupAge.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioYes) {
                val intent = Intent(this, GenderActivity::class.java)
                startActivity(intent)
            }
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupAge.checkedRadioButtonId
            if (selectedId == R.id.radioYes) {
                val intent = Intent(this, GenderActivity::class.java)
                startActivity(intent)
            } else if (selectedId == R.id.radioNo) {
                Toast.makeText(this, "You must be at least 18 years old.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
