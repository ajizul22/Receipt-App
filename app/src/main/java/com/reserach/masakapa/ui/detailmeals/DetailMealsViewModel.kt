package com.reserach.masakapa.ui.detailmeals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reserach.masakapa.api.RetrofitClient
import com.reserach.masakapa.data.model.detailmeals.DetailMealsResponse
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response

class DetailMealsViewModel: ViewModel() {
    val mealsDetail = MutableLiveData<DetailMealsResponse.DetailMeals>()
    val isSuccess = MutableLiveData<Boolean>()

    fun setDetailMeals(id: String) {
        RetrofitClient.apiInstance
            .getDetailMealsById(id)
            .enqueue(object : Callback<DetailMealsResponse>{
                override fun onResponse(
                    call: Call<DetailMealsResponse>,
                    response: Response<DetailMealsResponse>
                ) {
                    if (response.isSuccessful) {
                        mealsDetail.value = response.body()?.meals?.get(0)
                        isSuccess.value = true
                    }
                }

                override fun onFailure(call: Call<DetailMealsResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    isSuccess.value = false
                }
            })
    }

    fun getDetailMeals(): LiveData<DetailMealsResponse.DetailMeals>{
        return mealsDetail
    }
}