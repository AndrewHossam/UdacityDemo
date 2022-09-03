package com.github.andrewhossam.udacitydemo

import retrofit2.Call
import retrofit2.http.GET


interface MealService {
    @GET("random.php")
    fun getRandomMeal(): Call<RandomMealResponse>
}