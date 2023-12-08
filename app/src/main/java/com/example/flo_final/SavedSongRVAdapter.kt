package com.example.flo_final

import android.annotation.SuppressLint
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo_final.databinding.ItemSongBinding

class SavedSongRVAdapter() : RecyclerView.Adapter<SavedSongRVAdapter.ViewHolder>() {

    private val songs = ArrayList<Song>()
    interface MyItemClickListner{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListner

    fun setMyItemClickListner(itemClickListner: MyItemClickListner){
        mItemClickListener = itemClickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSongRVAdapter.ViewHolder {
        val binding: ItemSongBinding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedSongRVAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])

        holder.binding.itemSongMoreIv.setOnClickListener {
            mItemClickListener.onRemoveSong(songs[position].id) // 좋아요 취소로 업데이트하는 메서드
            removeSong(position) // 현재 화면에서 아이템을 제거
        }

    }

    override fun getItemCount(): Int = songs.size

    inner class ViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song : Song){
            binding.itemLockerAlbumTitleTv.text = song.title
            binding.itemLockerAlbumSingerTv.text = song.singer
            binding.itemLockerAlbumCoverImgIv.setImageResource(song.coverImg!!)
        }
    }

    interface OnItemClickListener {
        fun onRemoveAlbum(songId: Int)
    }

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged") // 경고 무시 어노테이션
    fun addSongs(songs: ArrayList<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

}