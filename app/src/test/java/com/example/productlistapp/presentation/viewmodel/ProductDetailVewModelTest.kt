package com.example.productlistapp.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productlistapp.common.UiState
import com.example.productlistapp.data.model.ProductListDTO
import com.example.productlistapp.data.network.ApiService
import com.example.productlistapp.data.repository.RepositoryImpl
import com.example.productlistapp.domain.repository.Repository
import com.example.productlistapp.domain.usecase.GetProductDetailUseCase
import com.example.productlistapp.models.mocks.MocksProductDataModelDetail
import com.example.productlistapp.models.mocks.toResponseApiProductDetails
import com.example.productlistapp.models.mocks.toResponseProductsDetails
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ProductDetailVewModelTest {
    private lateinit var mRepo: Repository
    private lateinit var mViewModel: ProductDetailVewModel
    private lateinit var mProductDetailUseCase: GetProductDetailUseCase
    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var apiService: ApiService

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mRepo = RepositoryImpl(apiService)
        Dispatchers.setMain(testDispatcher)
        mProductDetailUseCase = GetProductDetailUseCase(mRepo as RepositoryImpl)
        mViewModel = ProductDetailVewModel(mProductDetailUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testProductDetailSuccess() = runTest(UnconfinedTestDispatcher()) {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val mockCatsData = MocksProductDataModelDetail()
        val apiResponse = toResponseApiProductDetails(mockCatsData)
        val verifyData = toResponseProductsDetails(mockCatsData)

        whenever(apiService.getProductDetailsAPI("1")).thenReturn(apiResponse.body())

        // Act
        val result = mProductDetailUseCase.invoke("1").toList()

        // Assert
        assert(result[1] is UiState.Success)
        assertEquals(
            "both are not equal",
            result[1].data?.id, verifyData.id
        )
        assertEquals(
            "both are not equal",
            result[1].data?.title, verifyData.title
        )
    }

    @Test
    fun testGetProductDetailErrorState() = runTest(UnconfinedTestDispatcher()) {

        // Define a sample error response for the service
        val errorResponse = Response.error<ProductListDTO>(
            400, "Error message".toResponseBody(
                "application/json".toMediaType()
            )
        )
        // Set up the mock to return the error response
        `when`(apiService.getProductDetailsAPI("1")).thenReturn(errorResponse.body())
        val result = mProductDetailUseCase.invoke("1").toList()
        //verify(apiService).getAllProductListAPI()
        assert(result[1] is UiState.Error)
        val errorResult = result[1] as UiState.Error
    }

    @Test
    fun testGetProductDetailException() = runTest(UnconfinedTestDispatcher()) {
        // Set up the mock to throw an exception
        `when`(apiService.getProductDetailsAPI("1")).thenThrow(RuntimeException("An error occurred"))
        val result = mProductDetailUseCase.invoke("1").toList()
        //verify(apiService).getAllProductListAPI()
        assert(result[1] is UiState.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }
}