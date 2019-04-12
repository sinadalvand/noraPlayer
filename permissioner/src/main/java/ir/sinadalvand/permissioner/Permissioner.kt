package ir.sinadalvand.permissioner


import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*


/*
* This class created for request permission group or single by material Dialog
*
* */

class Permissioner {
    class Builder{
        fun with(activity: AppCompatActivity):POptions{
            return POptions(PSource(activity))
        }
    }
}