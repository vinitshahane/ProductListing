package com.example.productlistapp.data.repository


import com.example.productlistapp.common.toProductDetail
import com.example.productlistapp.common.toProductList
import com.example.productlistapp.data.network.ApiService
import com.example.productlistapp.domain.model.ProductDetail
import com.example.productlistapp.domain.model.ProductItem
import com.example.productlistapp.domain.repository.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun getProductList(): List<ProductItem> {
       return apiService.getAllProductListAPI().map { it.toProductList() }
    }

    override suspend fun getProductDetail(id: String): ProductDetail {
        return apiService.getProductDetailsAPI(id).toProductDetail()
    }

}