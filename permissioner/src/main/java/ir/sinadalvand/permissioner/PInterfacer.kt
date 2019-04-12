package ir.sinadalvand.permissioner

import ir.sinadalvand.permissioner.interfaces.*

class PInterfacer() {
    var gerendlistnenr: OnGrantedListener? = null
    var deniedlistnenr: OnDeniedListener? = null
    var recentlistnenr: OnRecentListener? = null
    var laterclicklistener: OnLaterClickListener? = null
    var finishListener: OnFinishListener? = null
}