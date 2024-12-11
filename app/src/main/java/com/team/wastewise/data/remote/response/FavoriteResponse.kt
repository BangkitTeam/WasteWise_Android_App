package com.team.wastewise.data.remote.response


data class FavoriteResponse(
	val data: FavoriteData,
	val message: String,
	val status: Int
)

data class FavoriteData(
	val userId: Int,
	val favorite: List<FavoriteItem>
)

data class FavoriteItem(
	val imageUrl: String,
	val description: String,
	val id: Int,
	val title: String
)

data class AddFavoriteRequest(
	val userId: Int,
	val userRecommendationId: Int
)

data class AddFavoriteResponse(
	val data: FavoriteItem,
	val message: String
)