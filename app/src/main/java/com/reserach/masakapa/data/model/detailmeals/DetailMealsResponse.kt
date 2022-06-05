package com.reserach.masakapa.data.model.detailmeals

data class DetailMealsResponse(val meals: List<DetailMeals>) {
    data class DetailMeals(
        val idMeal: String,
        val strMeal: String,
        val strCategory: String,
        val strInstructions: String,
        val strMealThumb: String,
        val strTags: String
    )
}
