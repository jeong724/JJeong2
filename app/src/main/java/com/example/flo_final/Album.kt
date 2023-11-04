package com.example.flo_final

data class Album(
    var  title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var songs: ArrayList<Song>? = null
)
