package com.example.flo_final

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_final.databinding.FragmentSongfileBinding
import com.example.flo_final.databinding.FragmentVideoBinding

class SongfileFragment : Fragment(){

    lateinit var binding: FragmentSongfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSongfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}