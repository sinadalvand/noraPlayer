package ir.sinadalvand.permissioner

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.app.Application


class PsharePref constructor(val context: Context) {

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private lateinit var editor: SharedPreferences.Editor

    init {
        editor = prefs.edit()
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, 0)
    }

    fun setInt(key: String,value:Int) {
        editor.putInt(key,value).apply()
    }


    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    fun setBoolen(key: String,value:Boolean) {
        editor.putBoolean(key,value).apply()
    }

}