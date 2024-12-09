package com.team.wastewise.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class FileUploadResponse(

	@field:SerializedName("file")
	val file: UploadedFile,

	@field:SerializedName("message")
	val message: String,

	val data: Data
)

data class 	UploadedFile(

	@field:SerializedName("image_name")
	val imageName: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("size")
	val size: Int,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("description")
	val description: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("userId")
	val userId: Int
)

@Parcelize
data class Data(
	val user_id: Int,
	val image_url: String,
	val confidence: Int,
	val prediction: String,
	val recommendations: List<Recommendation>
): Parcelable

@Parcelize
data class Recommendation(
	val id: Int,
	val title: String,
	val description: String,
	val imageUrl: String
): Parcelable
