package ir.sinadalvand.permissioner

import android.os.Build
import ir.sinadalvand.permissioner.interfaces.*

class PCallbacker(private var source: PSource) : CallableInterface {


    private lateinit var grentedlistnenr: OnGrantedListener
    private lateinit var deniedlistnenr: OnDeniedListener
    private lateinit var recentlistnenr: OnRecentListener
    private lateinit var laterlistnenr: OnLaterClickListener

    private val callabledata = PInterfacer()
    private val dialog: PDialog = PDialog(source, callabledata)


    override fun onGranted(listnenr: OnGrantedListener): CallableInterface {
        callabledata.gerendlistnenr = grentedlistnenr
        return this
    }

    override fun onDenied(listnenr: OnDeniedListener): CallableInterface {
        callabledata.deniedlistnenr = listnenr
        return this
    }

    override fun OnRecent(listnenr: OnRecentListener): CallableInterface {
        callabledata.recentlistnenr = listnenr
        return this
    }

    override fun LaterPressed(listnenr: OnLaterClickListener): CallableInterface {
        callabledata.laterclicklistener = listnenr
        return this
    }

    override fun OnFinish(listnenr: OnFinishListener): CallableInterface {
        callabledata.finishListener = listnenr
        return this
    }

    override fun show() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialog.show(source.perms)
        } else {
            if (::grentedlistnenr.isInitialized) grentedlistnenr.onGrantedResult(source.perms)
        }
    }


}