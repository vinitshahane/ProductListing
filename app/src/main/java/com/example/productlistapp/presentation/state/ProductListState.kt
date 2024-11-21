package com.example.productlistapp.presentation.state

import com.example.productlistapp.domain.model.ProductItem


data class ProductListState(
    val isLoading: Boolean = false,
    val data: List<ProductItem>? = null,
    var error: String = ""
)