package com.example.btest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pasindu.gitregistry.data.api.ApiService
import com.pasindu.gitregistry.data.model.Repo
import retrofit2.HttpException
import java.io.IOException

class PagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Repo>() {

    companion object {
        private const val statingPage = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val page = params.key ?: statingPage

        return try {
            val response = apiService.searchRepositories(
                query = query,
                page = page,
                perPage = params.loadSize
     )

            val repos = response.items

            LoadResult.Page(
                data = repos,
                prevKey = if (page == statingPage) null else page - 1,
                nextKey = if (repos.isEmpty()) null else page + 1
            ) } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) }
    }
}
