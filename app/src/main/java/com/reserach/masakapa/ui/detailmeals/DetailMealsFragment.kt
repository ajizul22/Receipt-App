package com.reserach.masakapa.ui.detailmeals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.reserach.masakapa.R
import com.reserach.masakapa.databinding.FragmentDetailMealsBinding
import com.reserach.masakapa.databinding.FragmentReceiptByCategoryBinding
import com.reserach.masakapa.ui.receiptbycategoryfragment.ReceiptByCategoryFragment
import com.reserach.masakapa.ui.receiptbycategoryfragment.ReceiptByCategoryViewModel

class DetailMealsFragment: Fragment() {
    companion object {
        const val ARG_ID = "id"


        fun newInstance(id: String): DetailMealsFragment {
            val fragment = DetailMealsFragment()

            val bundle = Bundle().apply {
                putString(ARG_ID, id)
            }

            fragment.arguments = bundle

            return fragment
        }
    }

    private lateinit var id: String
    private lateinit var bind: FragmentDetailMealsBinding
    private lateinit var viewModel: DetailMealsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_meals,container,false)
        id = arguments?.getString(ARG_ID).toString()

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailMealsViewModel::class.java)

        initRequestData()
        initComponent()
        initData()

        return bind.root
    }

    private fun initRequestData() {
        viewModel.setDetailMeals(id)
        showLoading(true)
    }

    private fun initComponent() {

    }

    private fun initData() {
        viewModel.getDetailMeals().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                bind.data = it

                if (it.strTags != null) {
                    bind.tvTags.text = it.strTags
                } else {
                    bind.tvTags.text = "-"
                }

                Glide.with(context!!)
                    .load(it.strMealThumb)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(bind.imgMeals)
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
            bind.container.visibility = View.GONE
        } else {
            bind.progressBar.visibility = View.GONE
            bind.container.visibility = View.VISIBLE
        }
    }

}