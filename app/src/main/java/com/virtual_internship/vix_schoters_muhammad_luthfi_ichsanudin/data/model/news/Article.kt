package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.news

data class Article(
    val author: String,
    val description: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)