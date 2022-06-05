package com.reserach.masakapa.ui.searchmeals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reserach.masakapa.api.RetrofitClient
import com.reserach.masakapa.data.model.detailmeals.DetailMealsResponse
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMealsViewModel: ViewModel() {

    val listMeals = MutableLiveData<List<ReceiptByCategoriesResponse.Meals>>()
    val isSuccess = MutableLiveData<Boolean>()

    fun setSearchMealsByName(name: String){
        RetrofitClient.apiInstance
            .getSearchMealsByName(name)
            .enqueue(object : Callback<ReceiptByCategoriesResponse> {
                override fun onResponse(
                    call: Call<ReceiptByCategoriesResponse>,
                    response: Response<ReceiptByCategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        isSuccess.value = true

                        listMeals.value = response.body()?.meals
                    }
                }

                override fun onFailure(call: Call<ReceiptByCategoriesResponse>, t: Throwable) {
                   isSuccess.value = false
                }
            })
    }

    fun getSearchMealsByName(): LiveData<List<ReceiptByCategoriesResponse.Meals>>{
        return listMeals
    }

}