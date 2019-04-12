package ir.sinadalvand.player.nora.view.Activities

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.animation.AlphaAnimation
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.utils.AppCompatFragmentInjector
import ir.sinadalvand.player.nora.view.Adapters.Fragments.LandingFragmentManager
import ir.sinadalvand.player.nora.view.Fragments.QuickControlFragment
import ir.sinadalvand.player.nora.view.Utils.CoolAnimationUtils
import ir.sinadalvand.player.nora.view.Utils.CoolAnimationUtils.runMultiAnimation
import ir.sinadalvand.player.nora.view.Utils.ImageUtils
import ir.sinadalvand.player.nora.view.Utils.LandingUtils
import ir.sinadalvand.player.nora.view.Utils.LandingUtils.setDrawer
import ir.sinadalvand.player.nora.view.Utils.Utils
import ir.sinadalvand.player.nora.viewmodel.LandingViewModel
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_landing.*
import kotlinx.android.synthetic.main.activity_landing_content.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.abs.IPagerNavigator
import javax.inject.Inject


class LandingActivity : AppCompatFragmentInjector() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        val userModel: LandingViewModel = ViewModelProviders.of(this).get(LandingViewModel::class.java)


        initDrawer()
        initTablayout()
        initSearchbar()


        val frag = supportFragmentManager
        val trans = frag.beginTransaction()
        trans.replace(R.id.landing_main_quickcontrol, QuickControlFragment())
        trans.commitAllowingStateLoss()

        Blurry.with(this).radius(25).sampling(5).from(ImageUtils.drawableToBitmap(landing_backlogo.drawable)).into(landing_backlogo);
    }


    private fun initSearchbar() {
        landing_appbar_search.setOnBarClickListener { it -> if (it) showSearch() else hideSeach() }
    }


    private fun initTablayout() {
        landing_tablayout.setBackgroundColor(Color.TRANSPARENT)
        landing_tablayout.navigator = LandingUtils.getNavigator(this, landing_main_pager) as IPagerNavigator
        landing_main_pager.adapter = LandingFragmentManager(supportFragmentManager)
        landing_tablayout.onPageSelected(1)
        landing_main_pager.currentItem = 1
        landing_main_pager.offscreenPageLimit = 4
        ViewPagerHelper.bind(landing_tablayout, landing_main_pager)



        landing_main_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(page: Int) {

                Log.e("APP", "Current page : " + page + "      adapter: " + landing_main_pager.adapter!!.count)
                if (landing_main_pager.adapter != null && page == landing_main_pager.adapter!!.count - 1 && !landing_appbar_search.isOpen) {
                    showSearch()
                }

            }

        })


    }

    private fun initDrawer() {
        setDrawer(landing_drawer)
        landing_appbar_toggle.setOnClickListener {
            if (landing_drawer.drawerState == ElasticDrawer.STATE_CLOSED) landing_drawer.openMenu(true) else landing_drawer.closeMenu(true)
        }
    }


    var lastPage = 1;
    private fun showSearch() {

        val resizer = CoolAnimationUtils.ResizeAnimation(landing_tablayout, -1, 0)
        val fader = AlphaAnimation(1f, 0f)

        lastPage = if (landing_main_pager.adapter != null) landing_main_pager.currentItem else 1
        landing_main_pager.setCurrentItem(landing_main_pager.adapter!!.count - 1, false)

        landing_tablayout.startAnimation(runMultiAnimation(resizer, fader))

//        landing_main_pager.adapter.instantiateItem(landing_main_pager,)

    }


    private fun hideSeach() {
        val resizer = CoolAnimationUtils.ResizeAnimation(landing_tablayout, -1, Utils.getAttr(this, android.R.attr.actionBarSize) as Int)
        val fader = AlphaAnimation(0f, 1f)
        Log.e("APP", " lastpage : " + lastPage)
        landing_main_pager.setCurrentItem(lastPage, false)
        landing_tablayout.startAnimation(runMultiAnimation(resizer, fader))
    }

    override fun onBackPressed() {
        if (landing_drawer.drawerState == ElasticDrawer.STATE_OPEN) {
            landing_drawer.closeMenu(true)
        } else if (landing_appbar_search.isOpen) {
            landing_appbar_search.close()
        } else {

            super.onBackPressed()
        }
    }
}



