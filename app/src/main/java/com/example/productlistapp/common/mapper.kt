package com.example.productlistapp.common

import com.example.productlistapp.data.model.ProductListDTO
import com.example.productlistapp.domain.model.ProductDetail
import com.example.productlistapp.domain.model.ProductItem


fun ProductListDTO.toProductList() : ProductItem {
    return ProductItem(id = this.id, image= this.image, title = this.title, description= this.description)
}

fun ProductListDTO.toProductDetail() : ProductDetail {
    return ProductDetail(
        category= this.category,
        description = this.description,
        id = this.id,
        image= this.image,
        price = this.price,
        title = this.title)
}