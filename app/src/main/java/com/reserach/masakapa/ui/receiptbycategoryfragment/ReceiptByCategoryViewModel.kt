package com.reserach.masakapa.ui.receiptbycategoryfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reserach.masakapa.api.RetrofitClient
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptByCategoryViewModel: ViewModel() {
    val listMeals = MutableLiveData<List<ReceiptByCategoriesResponse.Meals>> ()
    val isSuccess = MutableLiveData<Boolean>()

    fun setMealsByCategories(categories: String) {
        RetrofitClient.apiInstance
            .getMealsByCategories(categories)
            .enqueue(object : Callback<ReceiptByCategoriesResponse>{
                override fun onResponse(
                    call: Call<ReceiptByCategoriesResponse>,
                    response: Response<ReceiptByCategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        listMeals.value = response.body()?.meals
                        isSuccess.value = true
                    }
                }

                override fun onFailure(call: Call<ReceiptByCategoriesResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    isSuccess.value = false
                }
            })
    }

    fun getMeals(): LiveData<List<ReceiptByCategoriesResponse.Meals>> {
        return listMeals
    }

}