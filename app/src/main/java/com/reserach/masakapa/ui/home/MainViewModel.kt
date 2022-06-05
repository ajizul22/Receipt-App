package com.reserach.masakapa.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reserach.masakapa.api.RetrofitClient
import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listReceiptCategory = MutableLiveData<List<ReceiptCategoryResponse.CategoryDetailResponse>> ()
    val isSuccess = MutableLiveData<Boolean>()

    fun setReceiptCategory() {
        RetrofitClient.apiInstance
                .getAllCategories()
                .enqueue(object : Callback<ReceiptCategoryResponse>{
                    override fun onResponse(call: Call<ReceiptCategoryResponse>, response: Response<ReceiptCategoryResponse>) {
                        if (response.isSuccessful) {
                            listReceiptCategory.value = response.body()?.categories
                            isSuccess.value = true
                        }
                    }

                    override fun onFailure(call: Call<ReceiptCategoryResponse>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                        isSuccess.value = false
                    }
                })
    }

    fun getReceiptCategory() :LiveData<List<ReceiptCategoryResponse.CategoryDetailResponse>> {
        return listReceiptCategory
    }

}