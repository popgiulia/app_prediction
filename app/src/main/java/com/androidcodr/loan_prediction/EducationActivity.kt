package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class EducationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education)

        val radioGroupEducation = findViewById<RadioGroup>(R.id.radioGroupEducation)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupEducation.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@EducationActivity, SelfEmployedActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioGraduate -> intent.putExtra("Education", 1)
                R.id.radioNotGraduate -> intent.putExtra("Education", 0)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupEducation.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@EducationActivity, SelfEmployedActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioGraduate -> intent.putExtra("Education", 1)
                    R.id.radioNotGraduate -> intent.putExtra("Education", 0)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
