package ir.sinadalvand.permissioner

class POptions constructor(var source: PSource) {

    fun permissions(perms: MutableList<PermissionerData>): PCallbacker {
        source.perms = perms
        return PCallbacker(source)
    }


    fun showDialog(showing: Boolean = true): POptions {
        source.showing_dialog = showing
        return this
    }


    fun showTwice(twice: Boolean = true):POptions{
        source.showing_dialog = twice
        return this
    }
}