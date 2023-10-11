package com.example.flo_final

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo_final.databinding.FragmentDetailBinding
import com.example.flo_final.databinding.FragmentSongBinding
import com.example.flo_final.databinding.FragmentVideoBinding

class SongFragment : Fragment() {

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSongBinding.inflate(inflater, container, false)

        binding.songLalacLayout.setOnClickListener{
            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
        }

        binding.songFluLayout.setOnClickListener {
            Toast.makeText(activity, "Flu", Toast.LENGTH_SHORT).show()
        }

        binding.songCoinLayout.setOnClickListener {
            Toast.makeText(activity, "Coin", Toast.LENGTH_SHORT).show()
        }


        binding.songSpringLayout.setOnClickListener {
            Toast.makeText(activity, "봄 안녕", Toast.LENGTH_SHORT).show()
        }

        binding.songCelebrityLayout.setOnClickListener {
            Toast.makeText(activity, "Coin", Toast.LENGTH_SHORT).show()
        }

        binding.songMixLayout.setOnClickListener {
            Toast.makeText(activity, "Coin", Toast.LENGTH_SHORT).show()
        }

        fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val binding = FragmentSongBinding.inflate(inflater, container, false)

            val myImageView = binding.songMixonTg
            val myImageView2 = binding.btnToggleOnTg

            myImageView.setOnClickListener {
                // 이미지뷰1을 숨김
                myImageView.visibility = View.INVISIBLE

                // 이미지뷰2를 보이게 함
                myImageView2.visibility = View.VISIBLE
            }

            return binding.root
        }



        return binding.root
    }
}