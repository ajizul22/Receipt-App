package com.reserach.masakapa.ui.receiptbycategoryfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reserach.masakapa.R
import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import com.reserach.masakapa.databinding.FragmentReceiptByCategoryBinding
import com.reserach.masakapa.ui.middle.MiddleActivity
import com.reserach.masakapa.util.Constant
import com.reserach.masakapa.util.adapter.CategoryAdapter
import com.reserach.masakapa.util.adapter.ReceiptByCategoryAdapter

class ReceiptByCategoryFragment : Fragment(){
    companion object {
        const val ARG_CATEGORIES = "categories"


        fun newInstance(categoriesName: String): ReceiptByCategoryFragment {
            val fragment = ReceiptByCategoryFragment()

            val bundle = Bundle().apply {
                putString(ARG_CATEGORIES, categoriesName)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var categoriesName: String
    private lateinit var bind: FragmentReceiptByCategoryBinding
    private lateinit var viewModel: ReceiptByCategoryViewModel

    private lateinit var adapter: ReceiptByCategoryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt_by_category,container,false)
        categoriesName = arguments?.getString(ARG_CATEGORIES).toString()
        adapter = ReceiptByCategoryAdapter()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ReceiptByCategoryViewModel::class.java)

        initRequestData()
        initComponent()
        initData()


        return bind.root
    }

    private fun initRequestData() {
        viewModel.setMealsByCategories(categoriesName)
        showLoading(true)
    }

    private fun initComponent() {
        bind.apply {
            // RECYCLERVIEW
            rcvMeals.layoutManager = layoutManager
            rcvMeals.setHasFixedSize(true)
            rcvMeals.adapter = adapter

            tvTitle.text = categoriesName

            adapter.setOnItemClickCallback(object : ReceiptByCategoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ReceiptByCategoriesResponse.Meals) {
                    val intent = Intent(context, MiddleActivity::class.java)
                    intent.putExtra("KEY_OPEN_LAYOUT", Constant.KEY_OPEN_LAYOUT_DETAIL_MEALS)
                    intent.putExtra("KEY_PARAM_ONE", data.idMeal)
                    startActivity(intent)
//                    Toast.makeText(context, data.strMeal, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initData() {
        viewModel.getMeals().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.setList(it as ArrayList<ReceiptByCategoriesResponse.Meals>)
            }
        })

        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            bind.progressBar.visibility = View.VISIBLE
        } else {
            bind.progressBar.visibility = View.GONE
        }
    }

}