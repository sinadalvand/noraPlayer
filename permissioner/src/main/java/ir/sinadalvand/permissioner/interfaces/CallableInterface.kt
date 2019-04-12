package ir.sinadalvand.permissioner.interfaces

interface CallableInterface {

    fun onGranted(listnenr: OnGrantedListener): CallableInterface

    fun onDenied(listnenr: OnDeniedListener): CallableInterface

    fun OnRecent(listnenr: OnRecentListener): CallableInterface

    fun LaterPressed(listnenr: OnLaterClickListener) : CallableInterface

    fun OnFinish(listnenr: OnFinishListener) : CallableInterface

    fun show()

}

