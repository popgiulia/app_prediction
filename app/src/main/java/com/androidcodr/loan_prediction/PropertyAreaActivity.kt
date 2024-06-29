package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class PropertyAreaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_area)

        val radioGroupPropertyArea = findViewById<RadioGroup>(R.id.radioGroupPropertyArea)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        radioGroupPropertyArea.setOnCheckedChangeListener { group, checkedId ->
            val intent = Intent(this@PropertyAreaActivity, DependentsActivity::class.java)
            intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
            when (checkedId) {
                R.id.radioUrban -> intent.putExtra("Property_Area", 1)
                R.id.radioSemiUrban -> intent.putExtra("Property_Area", 2)
                R.id.radioRural -> intent.putExtra("Property_Area", 0)
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val selectedId = radioGroupPropertyArea.checkedRadioButtonId
            if (selectedId != -1) {
                val intent = Intent(this@PropertyAreaActivity, DependentsActivity::class.java)
                intent.putExtras(intent.extras ?: Bundle())  // Preserve existing extras
                when (selectedId) {
                    R.id.radioUrban -> intent.putExtra("Property_Area", 1)
                    R.id.radioSemiUrban -> intent.putExtra("Property_Area", 2)
                    R.id.radioRural -> intent.putExtra("Property_Area", 0)
                }
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
