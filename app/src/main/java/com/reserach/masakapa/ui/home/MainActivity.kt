package com.reserach.masakapa.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.reserach.masakapa.R
import com.reserach.masakapa.util.adapter.CategoryAdapter
import com.reserach.masakapa.data.model.category.ReceiptCategoryResponse
import com.reserach.masakapa.databinding.ActivityMainBinding
import com.reserach.masakapa.ui.middle.MiddleActivity
import com.reserach.masakapa.util.Constant

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: CategoryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        adapter = CategoryAdapter()
        adapter.notifyDataSetChanged()
        layoutManager = GridLayoutManager(this, 2)

        initRequestData()
        initComponent()
        initData()

    }

    private fun initRequestData() {
        viewModel.setReceiptCategory()
        showLoading(true)
    }

    private fun initComponent() {
        bind.apply {

            // RECYCLERVIEW
            rcvCategory.layoutManager = layoutManager
            rcvCategory.setHasFixedSize(true)
            rcvCategory.adapter = adapter

            floatingActionButton.setOnClickListener {
                val intent = Intent(this@MainActivity, MiddleActivity::class.java)
                intent.putExtra("KEY_OPEN_LAYOUT", Constant.KEY_OPEN_LAYOUT_SEARCH)
                startActivity(intent)
            }

            adapter.setOnItemClickCallback(object : CategoryAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ReceiptCategoryResponse.CategoryDetailResponse) {
                    val intent = Intent(this@MainActivity, MiddleActivity::class.java)
                    intent.putExtra("KEY_OPEN_LAYOUT", Constant.KEY_OPEN_LAYOUT_RECEIPT_BY_CATEGORY)
                    intent.putExtra("KEY_PARAM_ONE", data.strCategory)
                    startActivity(intent)
                }
            })

            etQuery.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    adapter.filter.filter(p0)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

        }
    }

    private fun initData() {
        viewModel.getReceiptCategory().observe(this, {
            if (it != null) {
                adapter.setList(it as ArrayList<ReceiptCategoryResponse.CategoryDetailResponse>)
            }
        })

        viewModel.isSuccess.observe(this, {
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