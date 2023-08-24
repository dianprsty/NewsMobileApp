package id.dianprasetyo.newsmobileapp.model

import com.google.gson.annotations.SerializedName

data class ResponseNewsDetail(

	@field:SerializedName("important")
	val important: String? = null,

	@field:SerializedName("detail_post")
	val detailPost: DetailPost? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DetailPost(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("post_content")
	val postContent: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("pusblised_at")
	val pusblisedAt: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("image_desc")
	val imageDesc: String? = null
)
