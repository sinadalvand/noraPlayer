package ir.sinadalvand.player.nora.view.Utils

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class ColorFlipPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    private var mChangePercent = 0.5f

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
        } else {
            setTextColor(mSelectedColor);
        }
        super.onLeave(index, totalCount, leavePercent, leftToRight)
    }

    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor)
        } else {
            setTextColor(mNormalColor)
        }
        super.onEnter(index, totalCount, enterPercent, leftToRight)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
    }

    fun getChangePercent(): Float {
        return mChangePercent
    }

    fun setChangePercent(changePercent: Float) {
        mChangePercent = changePercent
    }
}