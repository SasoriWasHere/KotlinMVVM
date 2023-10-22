package com.example.kotlinwork.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinwork.pojo.Meal
import com.example.kotlinwork.pojo.MealList
import com.example.kotlinwork.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class HomeViewModel():ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : retrofit2.Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal:Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    // Handle unsuccessful response
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
            }
        })
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }


}