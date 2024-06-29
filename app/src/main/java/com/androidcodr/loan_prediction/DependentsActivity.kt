package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class DependentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependents)

        val radioGroupDependents = findViewById<RadioGroup>(R.id.radioGroupDependents)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupDependents.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@DependentsActivity, ApplicantIncomeActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioZero -> intent.putExtra("Dependents", 0)
                R.id.radioOne -> intent.putExtra("Dependents", 1)
                R.id.radioTwo -> intent.putExtra("Dependents", 2)
                R.id.radioThreePlus -> intent.putExtra("Dependents", 3)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupDependents.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@DependentsActivity, ApplicantIncomeActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioZero -> intent.putExtra("Dependents", 0)
                    R.id.radioOne -> intent.putExtra("Dependents", 1)
                    R.id.radioTwo -> intent.putExtra("Dependents", 2)
                    R.id.radioThreePlus -> intent.putExtra("Dependents", 3)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
