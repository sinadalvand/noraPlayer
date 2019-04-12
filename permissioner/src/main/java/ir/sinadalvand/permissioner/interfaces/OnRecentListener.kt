package ir.sinadalvand.permissioner.interfaces

import ir.sinadalvand.permissioner.PermissionerData

interface OnRecentListener {
    fun onRecentResult(perm: PermissionerData, granted: Boolean)
}