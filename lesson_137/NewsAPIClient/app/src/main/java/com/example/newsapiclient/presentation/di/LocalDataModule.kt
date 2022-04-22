package com.example.newsapiclient.presentation.di

import com.example.newsapiclient.data.db.ArticleDAO
import com.example.newsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.newsapiclient.data.repository.datasourceimpl.NewsLocalDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDatasource(articleDAO: ArticleDAO): NewsLocalDatasource {
        return NewsLocalDatasourceImpl(articleDAO)
    }
}