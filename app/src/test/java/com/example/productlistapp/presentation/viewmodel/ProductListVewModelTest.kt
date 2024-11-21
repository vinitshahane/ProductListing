package com.example.productlistapp.presentation.viewmodel

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productlistapp.common.UiState
import com.example.productlistapp.data.model.ProductListDTO
import com.example.productlistapp.data.network.ApiService
import com.example.productlistapp.data.repository.RepositoryImpl
import com.example.productlistapp.domain.repository.Repository
import com.example.productlistapp.domain.usecase.GetProductListUseCase
import com.example.productlistapp.models.mocks.MocksProductDataModel
import com.example.productlistapp.models.mocks.toResponseApiProduct
import com.example.productlistapp.models.mocks.toResponseProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ProductListVewModelTest   {
    private lateinit var mRepo: Repository
    private lateinit var mViewModel: ProductListVewModel
    private lateinit var mProductListUseCase: GetProductListUseCase
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
        mProductListUseCase = GetProductListUseCase(mRepo as RepositoryImpl)
        mViewModel = ProductListVewModel(mProductListUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetEmptyList() = runTest(UnconfinedTestDispatcher()) {
        val expectedRepositories = Response.success(listOf<ProductListDTO>())
        // Mock the API response
        `when`(apiService.getAllProductListAPI()).thenReturn(expectedRepositories.body())
        // Call the method under test
        val result = apiService.getAllProductListAPI()
        // Verify that the API method is called with the correct username
        verify(apiService).getAllProductListAPI()
        // Verify that the result matches the expected repositories
        assert(result == expectedRepositories.body())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetProductsApiData() = runTest(UnconfinedTestDispatcher()) {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val mockCatsData = MocksProductDataModel()
        val response = toResponseApiProduct(mockCatsData)
        val verifyData = toResponseProducts(mockCatsData)
        `when`(apiService.getAllProductListAPI()).thenReturn(response.body())// Mock the API response
        apiService.getAllProductListAPI()
        verify(apiService).getAllProductListAPI()

        //mViewModel.getCatsData()
        testDispatcher.scheduler.advanceUntilIdle() // Let the coroutine complete and changes propagate
        delay(1000)
        val result = mViewModel.productList.value.data
        assertEquals(
            "both are not equal",
            result?.size,
            verifyData.size
        )
        assertEquals(
            "both are not equal",
            result?.get(0)?.id ?: 0,
            verifyData[0].id
        )
    }
    @Test
    fun testGetProductListErrorState() = runTest(UnconfinedTestDispatcher()) {

        // Define a sample error response for the service
        val errorResponse = Response.error<List<ProductListDTO>>(
            400, "Error message".toResponseBody(
                "application/json".toMediaType()
            )
        )
        // Set up the mock to return the error response
        `when`(apiService.getAllProductListAPI()).thenReturn(errorResponse.body())
        val result = mProductListUseCase.invoke().toList()
        //verify(apiService).getAllProductListAPI()
        assert(result[1] is UiState.Error)
        val errorResult = result[1] as UiState.Error
    }

    @Test
    fun testGetProductListException() = runTest(UnconfinedTestDispatcher()) {
        // Set up the mock to throw an exception
        `when`(apiService.getAllProductListAPI()).thenThrow(RuntimeException("An error occurred"))
        val result = mProductListUseCase.invoke().toList()
        //verify(apiService).getAllProductListAPI()
        assert(result[1] is UiState.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }
}

