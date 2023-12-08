package com.example.flo_final

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_final.databinding.ActivitySongBinding
import java.util.Timer
import com.google.gson.Gson as Gson

class SongActivity : AppCompatActivity(){

    lateinit var binding : ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB : SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListner()

        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songNameTxt.text=intent.getStringExtra("title")!!
            binding.songSingerTxt.text=intent.getStringExtra("singer")!!
        }
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.SongDao().getSongs())
    }

    private fun initClickListner(){
        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener{
            moveSong(+1)
        }
        binding.songPreviousIv.setOnClickListener {
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("message", "뒤로가기 버튼 클릭")

        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initSong(){
        /*if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }*/
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.SongDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun moveSong(direct : Int){
        if (nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
        }
        else if (nowPos+direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
        }
        else {
            nowPos += direct


            timer.interrupt()
            startTimer()

            mediaPlayer?.release()
            mediaPlayer = null

            setPlayer(songs[nowPos])
        }

    }

    private fun getPlayingSongPosition(songId : Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song){
        binding.songNameTxt.text=song.title
        binding.songSingerTxt.text=song.singer
        binding.songStartTimeTv.text=String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text=String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.albumImgIb.setImageResource(song.coverImg!!)
        binding.songProgressbarView.progress=(song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer=MediaPlayer.create(this, music)

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)

    }

    private fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying=isPlaying
        timer.isPlaying=isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility= View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
            mediaPlayer?.start()
        }
        else{
            binding.songMiniplayerIv.visibility= View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
    }
    private fun startTimer(){
        timer=Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }
    inner class Timer(private val playTime: Int, var isPlaying: Boolean=true):Thread(){
        private var second : Int=0
        private var mills:Float=0f

        override fun run() {
            super.run()
            while (true){
                if (second >= playTime){
                    break
                }
                if (isPlaying){
                    sleep(50)
                    mills+=50

                    runOnUiThread {
                        binding.songProgressbarView.progress=((mills / playTime)*100).toInt()
                    }
                    if (mills % 1000 == 0f){
                        runOnUiThread {
                            binding.songStartTimeTv.text=String.format("%02d:%02d", second / 60, second % 60)
                        }
                        second++
                    }

                }

            }

        }
    }

    //사용자가 포커스 잃었을때 음악 중지
    override fun onPause() {
        super.onPause()


        songs[nowPos].second = ((binding.songProgressbarView.progress * songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId", songs[nowPos].id)

        editor.apply()


    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어가 가지고 있던 리소스 해제
        mediaPlayer = null
    }
}