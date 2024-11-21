package com.example.productlistapp.domain.usecase

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.productlistapp.data.repository.RepositoryImpl
import com.example.productlistapp.domain.repository.Repository
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing


class GetProductListUseCaseTest{
    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var repository: RepositoryImpl


}