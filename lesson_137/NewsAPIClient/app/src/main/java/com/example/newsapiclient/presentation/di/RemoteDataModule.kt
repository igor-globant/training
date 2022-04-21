package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import com.example.newsapiclient.data.repository.datasourceimpl.NewsRemoteDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDatasource(newsAPIService: NewsAPIService): NewsRemoteDatasource {
        return NewsRemoteDatasourceImpl(newsAPIService)
    }
}