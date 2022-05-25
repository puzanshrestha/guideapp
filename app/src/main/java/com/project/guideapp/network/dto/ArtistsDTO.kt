package com.project.guideapp.network

data class ArtistsDTO(
    val artType: String,
    val artistName: String,
    val audios: List<Audios>,
    val firstName: String,
    val id: String,
    val images: List<Images>,
    val lastName: String
)

data class Audios(
    val audioUri: String,
    val createdDate: String,
    val description: String,
    val id: String,
    val name: String
)

data class Images(
    val createdDate: String,
    val description: String,
    val id: String,
    val imageUri: String,
    val name: String
)