package com.example.kotlinwork.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlinwork.R
import com.example.kotlinwork.activities.MealActivity
import com.example.kotlinwork.databinding.FragmentHomeBinding
import com.example.kotlinwork.pojo.Meal

import com.example.kotlinwork.viewModel.HomeViewModel



class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal

    companion object{
        const val Meal_ID = "com.example.kotlinwork.fragments.idMeal"
        const val Meal_Name = "com.example.kotlinwork.fragments.nameMeal"
        const val Meal_Thumb = "com.example.kotlinwork.fragments.thumbMeal"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

    }

    private fun onRandomMealClick() {
        binding.homeRandomImage.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(Meal_ID,randomMeal.idMeal)
            intent.putExtra(Meal_Name,randomMeal.strMeal)
            intent.putExtra(Meal_Thumb,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment).load(value.strMealThumb).into(binding.homeRandomImage)
            this.randomMeal = value
        }

    }
}