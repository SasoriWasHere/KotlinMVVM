package com.example.kotlinwork.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlinwork.R
import com.example.kotlinwork.databinding.ActivityMealBinding
import com.example.kotlinwork.databinding.FragmentHomeBinding
import com.example.kotlinwork.fragments.HomeFragment
import com.example.kotlinwork.pojo.Meal
import com.example.kotlinwork.viewModel.HomeViewModel
import com.example.kotlinwork.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealid:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var mealMVVM:MealViewModel


    private lateinit var binding: ActivityMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMVVM = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setMealInformationToUserInterface()

        loadingDetails()
        mealMVVM.getMealDetails(mealid)
        observeMealLiveData()

        openYoutubewhenClick()

    }

    private fun openYoutubewhenClick() {
        binding.youtubeLinkBtn.setOnClickListener {
            val intent = Intent (Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealLiveData() {
        mealMVVM.observerMealDetailsLiveData().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                loadingDetailsDone()
                val meal = value
                binding.AreaMeal.text = "Area : ${meal!!.strArea}"
                binding.CategoryMeal.text = "Category : ${meal!!.strCategory}"
                binding.mealInstruction.text = meal!!.strInstructions
                youtubeLink = meal!!.strYoutube
            }

        })
    }

    private fun setMealInformationToUserInterface() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.CollapsingToolBar.title = mealName
    }

    private fun getMealInformationFromIntent() {

        val intent = intent
        mealid = intent.getStringExtra(HomeFragment.Meal_ID)!!
        mealName = intent.getStringExtra(HomeFragment.Meal_Name)!!
        mealThumb = intent.getStringExtra(HomeFragment.Meal_Thumb)!!


    }


    private fun loadingDetails(){
        binding.mealProgressBar.visibility = View.VISIBLE
        binding.AreaMeal.visibility = View.INVISIBLE
        binding.CategoryMeal.visibility = View.INVISIBLE
        binding.MealfavoriteBtn.visibility = View.INVISIBLE
        binding.mealInstruction.visibility = View.INVISIBLE
        binding.youtubeLinkBtn.visibility = View.INVISIBLE
    }
    private fun loadingDetailsDone(){
        binding.mealProgressBar.visibility = View.INVISIBLE
        binding.AreaMeal.visibility = View.VISIBLE
        binding.CategoryMeal.visibility = View.VISIBLE
        binding.MealfavoriteBtn.visibility = View.VISIBLE
        binding.mealInstruction.visibility = View.VISIBLE
        binding.youtubeLinkBtn.visibility = View.VISIBLE
    }
}