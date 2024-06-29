package com.androidcodr.loan_prediction

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("prediction") val prediction: String
)

data class RequestBody(
    @SerializedName("Gender") val gender: Int,
    @SerializedName("Married") val married: Int,
    @SerializedName("Education") val education: Int,
    @SerializedName("Self_Employed") val selfEmployed: Int,
    @SerializedName("Property_Area") val propertyArea: Int,
    @SerializedName("Dependents") val dependents: Int,
    @SerializedName("ApplicantIncome") val applicantIncome: Double,
    @SerializedName("CoapplicantIncome") val coapplicantIncome: Double,
    @SerializedName("LoanAmount") val loanAmount: Int,
    @SerializedName("Loan_Amount_Term") val loanAmountTerm: Int,
    @SerializedName("Credit_History") val creditHistory: Int
)
