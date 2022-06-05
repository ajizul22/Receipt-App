package com.reserach.masakapa.api

import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import com.reserach.masakapa.data.model.detailmeals.DetailMealsResponse
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface Api {
    @GET("categories.php")
    fun getAllCategories(): Call<ReceiptCategoryResponse>

    @GET("filter.php")
    fun getMealsByCategories(
        @Query("c") categories: String
    ) : Call<ReceiptByCategoriesResponse>

    @GET("lookup.php")
    fun getDetailMealsById(
        @Query("i") id: String
    ): Call<DetailMealsResponse>

    @GET("search.php")
    fun getSearchMealsByName(
        @Query("s") name: String
    ): Call<ReceiptByCategoriesResponse>
}