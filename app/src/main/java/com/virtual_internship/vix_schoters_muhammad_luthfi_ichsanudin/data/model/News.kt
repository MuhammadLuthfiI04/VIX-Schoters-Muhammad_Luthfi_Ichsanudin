package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)