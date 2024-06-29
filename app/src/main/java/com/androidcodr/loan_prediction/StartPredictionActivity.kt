package com.androidcodr.loan_prediction

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartPredictionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_prediction)

        val btnStart: Button = findViewById(R.id.btnStart)
        val btnBack: Button = findViewById(R.id.btnBack)

        btnStart.setOnClickListener {
            val intent = Intent(this, AgeCheckActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
