package ir.sinadalvand.permissioner.interfaces

import ir.sinadalvand.permissioner.PermissionerData

interface OnDeniedListener {
    fun onDeniedResult(perms: MutableList<PermissionerData>)
}