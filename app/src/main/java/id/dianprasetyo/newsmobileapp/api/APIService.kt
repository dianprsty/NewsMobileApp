package id.dianprasetyo.newsmobileapp.api

import id.dianprasetyo.newsmobileapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("category/{category}")
    fun getNewsByCategory(@Path("category") category : String) : Call<ResponseNews>
}