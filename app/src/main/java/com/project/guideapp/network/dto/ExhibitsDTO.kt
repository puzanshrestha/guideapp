package com.project.guideapp.network.dto


data class ExhibitsDTO(
    val artStyle: String,
    val artistId: String,
    val artistName: String,
    val audios: List<Audio>,
    val id: String,
    val images: List<Image>,
    val location: String,
    val name: String,
    val story: String
)

data class Audio(
    val audioUri: String,
    val createdDate: String,
    val description: String,
    val id: String,
    val name: String
)

data class Image(
    val createdDate: String,
    val description: String,
    val id: String,
    val imageUri: String,
    val name: String
)