package com.example.newsapiclient.data.repository.datasourceimpl

import com.example.newsapiclient.data.API.NewsAPIService
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.repository.datasource.NewsRemoteDatasource
import retrofit2.Response

class NewsRemoteDatasourceImpl(
    private val newsAPIService: NewsAPIService,
    private val country: String,
    private val page: Int
) : NewsRemoteDatasource {
    override suspend fun getTopHeadlines(): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }
}