package com.example.productlistapp.models.mocks


import com.example.productlistapp.data.model.ProductListDTO
import com.example.productlistapp.domain.model.ProductDetail
import com.example.productlistapp.domain.model.ProductItem
import retrofit2.Response

data class MocksProductDataModel(
    val category: String = "cloth",
    val description: String = "abc xyz",
    val id: Int = 1,
    val image: String = "a",
    val price: String = "12",
    val title: String = "abc"
)

fun toResponseApiProduct(mocksProductDataModel: MocksProductDataModel): Response<List<ProductListDTO>> {
    return Response.success(
        listOf(
            ProductListDTO(
                mocksProductDataModel.category,
                mocksProductDataModel.description,
                mocksProductDataModel.id,
                mocksProductDataModel.image,
                mocksProductDataModel.price,
                mocksProductDataModel.title
            )
        )
    )
}

fun toResponseProducts(mocksProductDataModel: MocksProductDataModel): List<ProductItem> {
    return listOf(
        ProductItem(
            id = mocksProductDataModel.id,
            image = mocksProductDataModel.image,
            title = mocksProductDataModel.title,
            description = mocksProductDataModel.description
        )
    )
}
data class MocksProductDataModelDetail(
    val category: String = "cloth",
    val description: String = "abc xyz",
    val id: Int = 1,
    val image: String = "a",
    val price: String = "12",
    val title: String = "abc"
)
fun toResponseApiProductDetails(mocksProductDataModel: MocksProductDataModelDetail): Response<ProductListDTO>{
    return Response.success(
        ProductListDTO(
                mocksProductDataModel.category,
                mocksProductDataModel.description,
                mocksProductDataModel.id,
                mocksProductDataModel.image,
                mocksProductDataModel.price,
                mocksProductDataModel.title
            )
    )
}

fun toResponseProductsDetails(mocksProductDataModel: MocksProductDataModelDetail): ProductItem {
    return ProductItem(
            id = mocksProductDataModel.id,
            image = mocksProductDataModel.image,
            title = mocksProductDataModel.title,
            description = mocksProductDataModel.description
        )

}