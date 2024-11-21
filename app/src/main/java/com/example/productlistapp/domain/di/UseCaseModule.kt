package com.example.productlistapp.domain.di


import com.example.productlistapp.data.repository.RepositoryImpl
import com.example.productlistapp.domain.usecase.GetProductDetailUseCase
import com.example.productlistapp.domain.usecase.GetProductListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun productListUseCaseProvider(repositoryImpl: RepositoryImpl) : GetProductListUseCase {
        return GetProductListUseCase(repositoryImpl)
    }

    @Provides
    @Singleton
    fun productDetailUseCaseProvider(repositoryImpl: RepositoryImpl) : GetProductDetailUseCase {
        return GetProductDetailUseCase(repositoryImpl)
    }

}