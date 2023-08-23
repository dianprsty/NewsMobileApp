package id.dianprasetyo.newsmobileapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseNews(

	@field:SerializedName("important")
	val important: String? = null,

	@field:SerializedName("pagination")
	val pagination: Any? = null,

	@field:SerializedName("posts")
	val posts: List<PostsItem?>? = null,

	@field:SerializedName("featured_post")
	val featuredPost: FeaturedPost? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		TODO("pagination"),
		TODO("posts"),
		TODO("featuredPost"),
		parcel.readValue(Int::class.java.classLoader) as? Int
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(important)
		parcel.writeValue(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseNews> {
		override fun createFromParcel(parcel: Parcel): ResponseNews {
			return ResponseNews(parcel)
		}

		override fun newArray(size: Int): Array<ResponseNews?> {
			return arrayOfNulls(size)
		}
	}
}

data class PostsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("premium_badge")
	val premiumBadge: String? = null,

	@field:SerializedName("pusblised_at")
	val pusblisedAt: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null
)

data class FeaturedPost(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("pusblised_at")
	val pusblisedAt: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("image_desc")
	val imageDesc: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null
)
