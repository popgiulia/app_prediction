package com.androidcodr.loan_prediction

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AmortizationScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amortization_schedule)

        val amortizationSchedule = intent.getSerializableExtra("amortization_schedule") as? ArrayList<Array<String>>
        val tblAmortizationSchedule = findViewById<TableLayout>(R.id.tblAmortizationSchedule)
        val btnBack: Button = findViewById(R.id.btnBack)

        amortizationSchedule?.forEachIndexed { index, row ->
            val tableRow = TableRow(this)
            if (index % 2 == 0) {
                tableRow.setBackgroundColor(getColor(R.color.row_background_even))
            } else {
                tableRow.setBackgroundColor(getColor(R.color.row_background_odd))
            }
            row.forEach { cell ->
                val textView = TextView(this)
                textView.text = cell
                textView.setPadding(8, 8, 8, 8)
                textView.gravity = Gravity.CENTER
                tableRow.addView(textView)
            }
            if (index != 0) {
                tblAmortizationSchedule.addView(tableRow)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
