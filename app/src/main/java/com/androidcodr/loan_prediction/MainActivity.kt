package com.androidcodr.loan_prediction

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPredict: Button = findViewById(R.id.btnPredict)
        val btnLoanCalculator: Button = findViewById(R.id.btnLoanCalculator)
        val btnSavings: Button = findViewById(R.id.btnSavings)
        val btnOptimalRepayment: Button = findViewById(R.id.btnOptimalRepayment)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        btnPredict.setOnClickListener {
            val intent = Intent(this, StartPredictionActivity::class.java)
            startActivity(intent)
        }

        btnLoanCalculator.setOnClickListener {
            val intent = Intent(this, LoanCalculatorActivity::class.java)
            startActivity(intent)
        }

        btnSavings.setOnClickListener {
            val intent = Intent(this, SavingsActivity::class.java)
            startActivity(intent)
        }

        btnOptimalRepayment.setOnClickListener {
            val intent = Intent(this, OptimalRepaymentActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.about_us -> {
                    showAboutUsDialog()
                    true
                }
                R.id.contact -> {
                    showContactDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun showAboutUsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("About Us")
        builder.setMessage("This app is dedicated to providing the best loan prediction and financial planning tools. Its mission is to help you make informed and smarter financial decisions.")
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        val dialog = builder.create()
        dialog.show()
    }
    private fun showContactDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Contact Us")
        builder.setMessage("Email: popgiuliaana@yahoo.com\nPhone: 0770828996")
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        val dialog = builder.create()
        dialog.show()
    }
}
