package com.reserach.masakapa.data.model.receiptbycategories

data class ReceiptByCategoriesResponse(val meals: List<Meals>) {
    data class Meals(
        val strMeal: String,
        val strMealThumb: String,
        val idMeal: String
    )
}
