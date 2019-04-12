package ir.sinadalvand.player.nora.view.Utils

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import com.mxn.soul.flowingdrawer_core.FlowingDrawer
import ir.sinadalvand.player.nora.R
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator


object LandingUtils {

    fun getTabLayoutData(context: Context): MutableList<String> {
        return arrayListOf(
                context.resources.getString(R.string.albums),
                context.resources.getString(R.string.songs),
                context.resources.getString(R.string.artist)
        ) as MutableList<String>

    }


    fun getNavigator(context: Context , pager: ViewPager): CommonNavigator {
        val Navigator = CommonNavigator(context)
        Navigator.isAdjustMode = true
        Navigator.isReselectWhenLayout = true
        Navigator.isSkimOver = true
        Navigator.isSmoothScroll = true
        Navigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return getTabLayoutData(context).size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = getTabLayoutData(context)[index]
                simplePagerTitleView.normalColor = Color.parseColor("#FFFFFF")
                simplePagerTitleView.selectedColor = Color.parseColor("#FFFFFF")
                simplePagerTitleView.setOnClickListener {
                    pager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_MATCH_EDGE
                indicator.lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.startInterpolator = AccelerateInterpolator() as Interpolator?
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#EF3F61"))
                return indicator
            }
        }

        return Navigator
    }


    fun setDrawer(drawer: FlowingDrawer) {
        drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL)
        drawer.setOnDrawerStateChangeListener(object : ElasticDrawer.OnDrawerStateChangeListener {
            override fun onDrawerStateChange(oldState: Int, newState: Int) {
                if (newState == ElasticDrawer.STATE_CLOSED) {

                }
            }

            override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {

            }
        })

    }
}