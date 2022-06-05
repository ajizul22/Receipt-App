package com.reserach.masakapa.data.model.category

data class ReceiptCategoryResponse( val categories: List<CategoryDetailResponse>) {
    data class CategoryDetailResponse(
            val idCategory: String,
            val strCategory: String,
            val strCategoryThumb: String
    )
}
