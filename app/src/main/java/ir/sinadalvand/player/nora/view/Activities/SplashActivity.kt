package ir.sinadalvand.player.nora.view.Activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ir.sinadalvand.permissioner.Permissioner
import ir.sinadalvand.permissioner.PermissionerData
import ir.sinadalvand.permissioner.interfaces.OnFinishListener
import ir.sinadalvand.player.nora.R
import ir.sinadalvand.player.nora.utils.AppCompatActivityInjector
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivityInjector() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Glide.with(this)
                .load(R.drawable.logo)
                .apply(RequestOptions().skipMemoryCache(true))
                .into(image_splash_logo)


        val permissionerData: MutableList<PermissionerData> = arrayListOf(
                PermissionerData(Manifest.permission.READ_EXTERNAL_STORAGE, "مجوز دسترسی به فایل", "برای بارگزاری و نمایش موزیک ها از حافظه به این دسترسی لازم است.")
                , PermissionerData(Manifest.permission.WRITE_EXTERNAL_STORAGE, "مجوز نوشتن روی حافظه", "برای ذخیره لیست پخش و برخی تنظیمات به این دسترسی لازم است.")) as MutableList<PermissionerData>

        Permissioner.Builder()
                .with(this)
                .showDialog(true)
                .permissions(permissionerData)
                .OnFinish(object : OnFinishListener {
                    override fun finish() {
                        startMainActivity()
                    }
                })
                .show()

        startAnimation()

    }


    private fun startMainActivity() {
        Thread {
            Thread.sleep(2500)
            startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
            finish()
        }.start()

    }

    private fun startAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.splash_fade)
        text_splash_name.startAnimation(animation)
    }

}