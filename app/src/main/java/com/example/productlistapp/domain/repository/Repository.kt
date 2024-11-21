package com.example.productlistapp.domain.repository

import com.example.productlistapp.domain.model.ProductDetail
import com.example.productlistapp.domain.model.ProductItem


interface Repository {

    suspend fun getProductList() : List<ProductItem>

    suspend fun getProductDetail(id : String) : ProductDetail

}