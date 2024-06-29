package com.androidcodr.loan_prediction
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PredictionActivity : AppCompatActivity() {
    private lateinit var predictionTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var infoTextView: TextView
    private lateinit var btnBackToMain: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        predictionTextView = findViewById(R.id.predictionTextView)
        resultTextView = findViewById(R.id.resultTextView)
        infoTextView = findViewById(R.id.infoTextView)
        btnBackToMain = findViewById(R.id.btnBackToMain)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.68.106:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        val gender = intent.getIntExtra("Gender", 0)
        val married = intent.getIntExtra("Married", 0)
        val education = intent.getIntExtra("Education", 0)
        val selfEmployed = intent.getIntExtra("SelfEmployed", 0)
        val propertyArea = intent.getIntExtra("PropertyArea", 0)
        val dependents = intent.getIntExtra("Dependents", 0)
        val applicantIncome = intent.getDoubleExtra("ApplicantIncome", 0.0)
        val coapplicantIncome = intent.getDoubleExtra("CoapplicantIncome", 0.0)
        val loanAmount = intent.getIntExtra("LoanAmount", 0)
        val loanAmountTerm = intent.getIntExtra("LoanAmountTerm", 0)
        val creditHistory = intent.getIntExtra("CreditHistory", 0)

        val requestBody = RequestBody(
            gender = gender,
            married = married,
            education = education,
            selfEmployed = selfEmployed,
            propertyArea = propertyArea,
            dependents = dependents,
            applicantIncome = applicantIncome,
            coapplicantIncome = coapplicantIncome,
            loanAmount = loanAmount,
            loanAmountTerm = loanAmountTerm,
            creditHistory = creditHistory
        )

        apiService.predictLoanStatus(requestBody).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.prediction
                    if (prediction == "Loan will be granted.") {
                        resultTextView.text = "Loan will be granted!"
                        resultTextView.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                    } else {
                        resultTextView.text = "Loan not will be granted!"
                        resultTextView.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                    }
                } else {
                    predictionTextView.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                predictionTextView.text = "Error: ${t.message}"
            }
        })
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}