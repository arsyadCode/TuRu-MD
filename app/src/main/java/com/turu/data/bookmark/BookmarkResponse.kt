package com.turu.data.bookmark

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class BookmarkResponse(

	@field:SerializedName("BookmarkResponse")
	val bookmarkResponse: List<BookmarkResponseItem>
)

@Parcelize
@Entity(tableName = "bookmark")
data class BookmarkResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
): Parcelable
