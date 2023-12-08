package com.example.flo_final

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums() : List<Album>

    @Insert
    fun likeAlbum(like: Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId") //앨범아이디와 유저아이디를 비교해서 liketable에 있는지 반환
    fun isLikedAlbum(userId:Int, albumId:Int) : Int?

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    fun disLikedAlbum(userId:Int, albumId:Int)

    @Query("SELECT AT. * FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT .albumId = AT .id WHERE LT .userId = :userId") // 앨범테이블을 lt라 읽는다,lt와 at를 합친다
    fun getLikedAlbums(userId: Int) : List<Album>

}