package ir.sinadalvand.player.nora

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented Animate, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under Animate.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("ir.sinadalvand.player.cool", appContext.packageName)
    }
}
