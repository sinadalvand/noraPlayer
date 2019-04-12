package ir.sinadalvand.player.nora

import android.app.Activity
import android.app.Application
import co.ronash.pushe.Pushe
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import ir.sinadalvand.player.nora.di.components.DaggerPlayerComponent
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject


class CoolPlayer : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate() {
        super.onCreate()

        // make crash reporter active if is not debug mode
        if (BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())

        // make analytics service active
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        // init pushe (Push Notification Service)
        Pushe.initialize(this, true)


        // init Dagger
        DaggerPlayerComponent.builder()
                .application(this)
                .build()
                .inject(this)


        // init Calligraphy for change all activities font
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/vazir.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}

