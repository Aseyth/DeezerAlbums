package com.dalbum.deezeralbums.data.entity

data class PaginatedResponse<T>(
    val data: List<T>,
    val total: Int,
    val previous: String?,
    val next: String?
)