package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class GenderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gender)

        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@GenderActivity, MarriedActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioMale -> intent.putExtra("Gender", 0)
                R.id.radioFemale -> intent.putExtra("Gender", 1)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupGender.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@GenderActivity, MarriedActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioMale -> intent.putExtra("Gender", 0)
                    R.id.radioFemale -> intent.putExtra("Gender", 1)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
