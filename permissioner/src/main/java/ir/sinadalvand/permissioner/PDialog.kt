package ir.sinadalvand.permissioner

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.CardView
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Toast


class PDialog(val context: PSource, val callableInterface: PInterfacer) : PActivity.RequestPermissionListener, View.OnClickListener, SubmitButton.OnRefreshButton, DialogInterface.OnCancelListener, CompoundButton.OnCheckedChangeListener {


    private val dialog: Dialog = Dialog(context.context)
    private val grantedList: MutableList<PermissionerData> = arrayListOf()
    private val deniedList: MutableList<PermissionerData> = arrayListOf()

    private lateinit var Button_allow: SubmitButton
    private lateinit var Button_later: SubmitButton
    private lateinit var Text_title: SuperTextView
    private lateinit var Text_description: SuperTextView
    private lateinit var Image_icon: ImageView
    private lateinit var Card_settings: CardView
    private lateinit var CHECK_skip: AppCompatCheckBox

    private lateinit var listOfPermissions: MutableList<PermissionerData>
    private lateinit var permission: PermissionerData

    private var showSkipper = false
    private var finishCalled = false


    private val SHARE_COUNT_KEY = context.context.packageName + ".prmissions.count"
    private val SHARE_DONT_SHOW_KEY = context.context.packageName + ".prmissions.show"

    private val prefs = PsharePref(context.context)

    private val settingAnimation: Animation = AnimationUtils.loadAnimation(context.context, R.anim.settingslide)


    init {
        val layout: View = LayoutInflater.from(context.context).inflate(R.layout.permissioner_main, null)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(layout)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setGravity(Gravity.CENTER)
        binding()
    }

    private fun binding() {
        Image_icon = dialog.findViewById(R.id.image_permissioner)
        Text_title = dialog.findViewById(R.id.text_permissioner_title)
        Button_later = dialog.findViewById(R.id.button_permissioner_later)
        Button_allow = dialog.findViewById(R.id.button_permissioner_allow)
        Text_description = dialog.findViewById(R.id.text_permissioner_desc)
        Card_settings = dialog.findViewById(R.id.Card_permissioner_Settings)
        CHECK_skip = dialog.findViewById(R.id.button_permissioner_check)
        viewDefaultProperty()
    }


    private fun viewDefaultProperty() {
        Button_later.setOnClickListener(this)
        Button_allow.setOnClickListener(this)
        Card_settings.setOnClickListener(this)
        Button_allow.onRefreshButton(this)
        dialog.setOnCancelListener(this)
        CHECK_skip.setOnCheckedChangeListener(this)

        if (prefs.getInt(SHARE_COUNT_KEY) > 1) {
            showSkipper = true
        }
    }

    private fun show() {
        dialog.show()
    }

    private fun shouldShowDilaog() {
        if (context.showing_dialog && !dialog.isShowing)
            show()
    }

    fun show(perm: MutableList<PermissionerData>) {
        listOfPermissions = perm
        renderingData()
    }


    private fun renderingData() {

        if (!::listOfPermissions.isInitialized || listOfPermissions.size < 1) {
            finishAll()
            return
        }

        if (permissionchecker(listOfPermissions[0])) {
            listOfPermissions.removeAt(0)
            renderingData()
            return
        }

        if (showSkipper && CHECK_skip.visibility == View.GONE)
            CHECK_skip.visibility = View.VISIBLE

        if (prefs.getBoolean(SHARE_DONT_SHOW_KEY)) {
            finishAll()
            return
        }


        permission = listOfPermissions[0]


        if (permission.res_img == 0)
            Image_icon.visibility = View.GONE
        else
            Image_icon.visibility = View.VISIBLE



        Text_title.setText(permission.title, SuperTextView.AnimationMood.SLIDER)
        Text_description.setText(permission.description)

        shouldShowDilaog()
    }

    /*
    *         Send Permission for showing permission dialog
    * */
    private fun sendPermissionRequest() {
        PActivity.requestPermission(context.context, listOfPermissions[0], this)
    }


    /*
    *         Check Permission that have Granted Access ot not
    * */
    private fun permissionchecker(perm: PermissionerData): Boolean {
        return ContextCompat.checkSelfPermission(context.context, perm.permission) == PackageManager.PERMISSION_GRANTED
    }


    /*
    *         check permission list and if exist another not granted permission say is true
    * */
    private fun existanceNextPermission(): Boolean {
        return ::listOfPermissions.isInitialized && listOfPermissions.size > 0
    }


    private fun existanceNotGranted(): Boolean {
        if (existanceNextPermission())
            for (i in listOfPermissions) {
                if (!permissionchecker(i)) {
                    return true
                }
            }
        return false
    }


    private fun afterResultAction() {
        if (existanceNextPermission()) listOfPermissions.removeAt(0)

        if (existanceNotGranted())
            Button_allow.setNextExistance(true)
        else
            Button_allow.setNextExistance(false)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.button_permissioner_later -> {
                finishAll()
                callableInterface.laterclicklistener?.pressed()

            }


            R.id.button_permissioner_allow -> {
                sendPermissionRequest()
            }


            R.id.Card_permissioner_Settings -> {
                openPermissionSettings()
            }

        }
    }

    override fun onGranted(perm: PermissionerData) {
        grantedList.add(perm)
        afterResultAction()
        Button_allow.doResult(true)

        callableInterface.recentlistnenr?.onRecentResult(perm, true)
    }

    override fun onDenied(perm: PermissionerData) {
        deniedList.add(perm)
        afterResultAction()
        Button_allow.doResult(false)

        callableInterface.recentlistnenr?.onRecentResult(perm, false)

        settingAnimation()
    }

    override fun onRationale(perm: PermissionerData) {

    }


    override fun refreshed() {
        if (!existanceNotGranted() && dialog.isShowing)
            finishAll()
        else
            renderingData()
    }


    override fun onCancel(dialog: DialogInterface?) {
        prefs.setInt(SHARE_COUNT_KEY, prefs.getInt(SHARE_COUNT_KEY) + 1)
        finishCaller()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        prefs.setBoolen(SHARE_DONT_SHOW_KEY, isChecked)
    }


    private fun finishAll() {
        if (dialog.isShowing) dialog.cancel()

        callableInterface.deniedlistnenr?.onDeniedResult(deniedList)
        callableInterface.gerendlistnenr?.onGrantedResult(grantedList)

        finishCaller()


    }

    private fun finishCaller() {
        if (!finishCalled) {
            callableInterface.finishListener?.finish()
            finishCalled = true
        }
    }


    private fun settingAnimation() {
        if (Card_settings.visibility != View.VISIBLE) {
            Card_settings.startAnimation(settingAnimation)
            Card_settings.visibility = View.VISIBLE
        }
    }

    private fun openPermissionSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.context.packageName, null)
        intent.data = uri
        Toast.makeText(context.context, R.string.settings_description, Toast.LENGTH_LONG).show()
        context.context.startActivity(intent)
    }


}

