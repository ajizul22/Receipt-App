package com.reserach.masakapa.ui.middle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.reserach.masakapa.R
import com.reserach.masakapa.databinding.ActivityMainBinding
import com.reserach.masakapa.databinding.ActivityMiddleBinding
import com.reserach.masakapa.ui.detailmeals.DetailMealsFragment
import com.reserach.masakapa.ui.receiptbycategoryfragment.ReceiptByCategoryFragment
import com.reserach.masakapa.ui.searchmeals.SearchMealsFragment
import com.reserach.masakapa.util.Constant.Companion.KEY_OPEN_LAYOUT_DETAIL_MEALS
import com.reserach.masakapa.util.Constant.Companion.KEY_OPEN_LAYOUT_RECEIPT_BY_CATEGORY
import com.reserach.masakapa.util.Constant.Companion.KEY_OPEN_LAYOUT_SEARCH

class MiddleActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMiddleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this,R.layout.activity_middle)

        val intent = intent
        val keylayout = intent.getIntExtra("KEY_OPEN_LAYOUT", 0)

        val param_str_one = intent.getStringExtra("KEY_PARAM_ONE")

        when (keylayout) {
            KEY_OPEN_LAYOUT_RECEIPT_BY_CATEGORY -> {
                val fragment = ReceiptByCategoryFragment.newInstance(param_str_one!!)
                    showFragment(fragment)
            }

            KEY_OPEN_LAYOUT_DETAIL_MEALS -> {
                val fragment = DetailMealsFragment.newInstance(param_str_one!!)
                showFragment(fragment)
            }

            KEY_OPEN_LAYOUT_SEARCH -> {
                val fragment = SearchMealsFragment()
                showFragment(fragment)
            }
            else -> {
                val fragmentReceiptByCategory = ReceiptByCategoryFragment()
                showFragment(fragmentReceiptByCategory)
            }

        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fg_container, fragment).commit()
    }
}