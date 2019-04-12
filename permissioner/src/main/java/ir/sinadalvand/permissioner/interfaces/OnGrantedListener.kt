package ir.sinadalvand.permissioner.interfaces

import ir.sinadalvand.permissioner.PermissionerData

interface OnGrantedListener {
    fun onGrantedResult(perms: MutableList<PermissionerData>)
}