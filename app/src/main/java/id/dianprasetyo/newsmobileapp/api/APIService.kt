package id.dianprasetyo.newsmobileapp.api

import id.dianprasetyo.newsmobileapp.model.ResponseNews
import id.dianprasetyo.newsmobileapp.model.ResponseNewsDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("category/{category}")
    fun getNewsByCategory(@Path("category") category : String) : Call<ResponseNews>

    @GET("detailpost/{slug}")
    fun getNewsDetail(@Path("slug") slug: String) : Call<ResponseNewsDetail>
}