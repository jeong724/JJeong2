package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_final.AlbumFragment
import com.example.flo_final.BannerFragment
import com.example.flo_final.BannerVPAdapter
import com.example.flo_final.HomeVPAdapter
import com.example.flo_final.MainActivity
import com.example.flo_final.R
import com.example.flo_final.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var indicator3: CircleIndicator3
    lateinit var viewPager2: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundIv)
        //binding.homeBannerIndicator.setViewPager(binding.homePannelViewpagerImg)


        val homeAdapter=HomeVPAdapter(this)
        binding.homePannelBackgroundIv.adapter=homeAdapter

        binding.homePannelAlbumTodayImg1.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()


        }

        
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homePannelViewpagerImg.adapter=bannerAdapter
        binding.homePannelViewpagerImg.orientation=ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }

}