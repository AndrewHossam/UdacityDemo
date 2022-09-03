package com.github.andrewhossam.udacitydemo

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvMealName = findViewById<TextView>(R.id.tvMealName)
        val ivMeal = findViewById<ImageView>(R.id.ivMeal)
        val btnSurpriseMe = findViewById<MaterialButton>(R.id.btnSurpriseMe)

        val retrofit = getRetrofit()
        val mealService = getMealsService(retrofit)

        btnSurpriseMe.setOnClickListener {
            mealService.getRandomMeal().enqueue(object : Callback<RandomMealResponse> {
                override fun onResponse(
                    call: Call<RandomMealResponse>,
                    response: Response<RandomMealResponse>,
                ) {
                    if (response.isSuccessful) {
                        tvMealName.text = response.body()!!.meals.first().strMeal
                        Picasso.get()
                            .load(response.body()!!.meals.first().strMealThumb)
                            .into(ivMeal)
                    }
                }

                override fun onFailure(call: Call<RandomMealResponse>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Sorry Something went wrong !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }
}

fun getRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun getMealsService(retrofit: Retrofit): MealService =
    retrofit.create(MealService::class.java)