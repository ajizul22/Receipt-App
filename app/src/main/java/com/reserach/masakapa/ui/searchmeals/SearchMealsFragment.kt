package com.reserach.masakapa.ui.searchmeals

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reserach.masakapa.R
import com.reserach.masakapa.data.model.receiptbycategories.ReceiptByCategoriesResponse
import com.reserach.masakapa.databinding.FragmentSearchMealsBinding
import com.reserach.masakapa.ui.middle.MiddleActivity
import com.reserach.masakapa.util.Constant
import com.reserach.masakapa.util.adapter.ReceiptByCategoryAdapter
import com.reserach.masakapa.util.adapter.SearchMealsAdapter

class SearchMealsFragment: Fragment() {

    private lateinit var bind: FragmentSearchMealsBinding
    private lateinit var viewModel: SearchMealsViewModel


    private lateinit var adapter: SearchMealsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_search_meals, container, false)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SearchMealsViewModel::class.java)

        adapter = SearchMealsAdapter()
        adapter.clear()
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        initRequestData()
        initComponent()
        initData()

        return bind.root
    }

    private fun initRequestData(){

    }

    private fun initComponent() {
        bind.apply {
            // RECYCLERVIEW
            rcvSearchMeals.layoutManager = layoutManager
            rcvSearchMeals.setHasFixedSize(true)
            rcvSearchMeals.adapter = adapter

            btnSearch.setOnClickListener {
                val name = bind.etQuery.text.toString()
                viewModel.setSearchMealsByName(name)
                showLoading(true)
            }

            // set enter key on search
            etQuery.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    val name = bind.etQuery.text.toString()
                    viewModel.setSearchMealsByName(name)
                    showLoading(true)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            adapter.setOnItemClickCallback(object : SearchMealsAdapter.OnItemClickCallback {
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
        viewModel.getSearchMealsByName().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.clear()
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