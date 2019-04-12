package ir.sinadalvand.permissioner

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class PChecker {

    companion object {
        fun grantedPermissions(context: Context, permission: String): Boolean {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            return false
        }


        fun rationalPermission(activityCompat: Activity, permission: String){
            ActivityCompat.shouldShowRequestPermissionRationale(activityCompat, Manifest.permission.READ_CONTACTS)
        }
    }

}