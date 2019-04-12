package ir.sinadalvand.permissioner

import android.content.Context

data class PSource(var context:Context, var perms:MutableList<PermissionerData> = arrayListOf(), var showing_dialog:Boolean = true, var showTwice:Boolean = true )