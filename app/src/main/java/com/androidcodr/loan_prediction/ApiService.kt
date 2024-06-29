package com.androidcodr.loan_prediction

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Interfața pentru serviciul Retrofit
interface ApiService {
    @POST("predict")
    fun predictLoanStatus(@Body requestBody: RequestBody): Call<ApiResponse>
}
