package com.example.productlistapp.presentation.state

import com.example.productlistapp.domain.model.ProductDetail

data class ProductDetailState(val isLoading : Boolean = false,
                              val data : ProductDetail? = null,
                              var error : String ="")