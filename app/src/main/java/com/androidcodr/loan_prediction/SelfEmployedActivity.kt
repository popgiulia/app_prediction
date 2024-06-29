package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class SelfEmployedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_employed)

        val radioGroupSelfEmployed = findViewById<RadioGroup>(R.id.radioGroupSelfEmployed)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupSelfEmployed.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@SelfEmployedActivity, PropertyAreaActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioYes -> intent.putExtra("Self_Employed", 1)
                R.id.radioNo -> intent.putExtra("Self_Employed", 0)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupSelfEmployed.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@SelfEmployedActivity, PropertyAreaActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioYes -> intent.putExtra("Self_Employed", 1)
                    R.id.radioNo -> intent.putExtra("Self_Employed", 0)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
