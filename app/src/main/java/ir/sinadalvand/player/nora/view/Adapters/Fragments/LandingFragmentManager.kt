package ir.sinadalvand.player.nora.view.Adapters.Fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ir.sinadalvand.player.nora.view.Fragments.AlbumsFragment
import ir.sinadalvand.player.nora.view.Fragments.ArtistFragment
import ir.sinadalvand.player.nora.view.Fragments.SearchFragment
import ir.sinadalvand.player.nora.view.Fragments.SongsFragment

class LandingFragmentManager(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return getLandingFragments()[position]
    }

    override fun getCount(): Int = 4


    private fun getLandingFragments(): MutableList<Fragment> {
        return arrayListOf(AlbumsFragment(), SongsFragment(), ArtistFragment(),SearchFragment())
    }


}