package com.example.flo_final

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo_final.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {
    lateinit var binding : FragmentLockerBinding

    private val information= arrayListOf("저장한 곡", "음악파일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLockerBinding.inflate(inflater, container, false)

        val lockerVPAdapter=LockerVPAdapter(this)
        binding.lockerVp.adapter=lockerVPAdapter
        TabLayoutMediator(binding.lockerTab,binding.lockerVp){
            tab, position -> tab.text=information[position]
        }.attach()

        return binding.root
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locker, container, false)
    }
}