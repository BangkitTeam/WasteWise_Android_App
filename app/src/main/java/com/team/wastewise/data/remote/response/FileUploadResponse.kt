package com.team.wastewise.data.remote.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse(

	@field:SerializedName("file")
	val file: UploadedFile,

	@field:SerializedName("message")
	val message: String
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
