package com.pasindu.gitregistry.data.api

import com.pasindu.gitregistry.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): SearchResponse
}