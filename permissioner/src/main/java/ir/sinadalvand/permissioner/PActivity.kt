package ir.sinadalvand.permissioner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat


final class PActivity : Activity() {


    companion object {

        var listener: RequestPermissionListener? = null

        private const val PERMISSION_ACTION = "permission_action"
        private const val RATIONALE_ACTION = "rationale_action"
        private const val PERMISSION_KEY = "perm"


        fun requestPermission(context: Context, perm: PermissionerData, listener: RequestPermissionListener) {
            PActivity.listener = listener
            val intent = Intent(context, PActivity::class.java)
            intent.action = PERMISSION_ACTION
            intent.putExtra(PERMISSION_KEY, perm)
            context.startActivity(intent)
        }


        fun rationalePermission(context: Context, perm: PermissionerData, listener: RequestPermissionListener) {
            PActivity.listener = listener
            val intent = Intent(context, PActivity::class.java)
            intent.action = RATIONALE_ACTION
            intent.putExtra(PERMISSION_KEY, perm)
            context.startActivity(intent)
        }
    }


    lateinit var perm: PermissionerData

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle: Bundle? = intent.extras
        when (intent.action) {

            PERMISSION_ACTION -> {
                if (bundle?.getParcelable<PermissionerData>(PERMISSION_KEY) != null) {
                    perm = bundle.getParcelable<PermissionerData>(PERMISSION_KEY)!!




                    if (listener != null) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, perm.permission)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                            ActivityCompat.requestPermissions(this, arrayOf(perm.permission), perm.requestCode)


                        } else {

                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(this, arrayOf(perm.permission), perm.requestCode)

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }

                    }else{

                    }
                }

            }

        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (listener!=null) {

            when (requestCode) {
                perm.requestCode -> {
                    // If request is cancelled, the result arrays are empty.
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        listener!!.onGranted(perm)
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {
                        listener!!.onDenied(perm)
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }

                }

                // Add other 'when' lines to check for other
                // permissions this app might request.
                else -> {
                    // Ignore all other requests.
                }
            }
        }
        this.finish()

    }

    interface RequestPermissionListener {
        fun onGranted(perm: PermissionerData)
        fun onDenied(perm: PermissionerData)
        fun onRationale(perm: PermissionerData)
    }
}
