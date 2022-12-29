package com.virtual_internship.vix_schoters_muhammad_luthfi_ichsanudin.data.model.github

data class DetailUser (
    val login: String,
    val id: Int,
    val avatar_url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val name: String,
    val company: String,
    val blog: String,
    val location: String,
    val public_repos: Int,
    val public_gists: Int,
    val followers: Int,
    val following: Int
)