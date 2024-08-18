package permissions

import android.Manifest.permission
import android.os.Build
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import com.example.benchmark.waitAndFind
import org.junit.Assert

/**
 * Because the app under test runs in a different process from the one running the instrumentation test,
 * the permission has to be granted manually by either:
 *
 * - tapping the Allow button
 *    ```kotlin
 *    val obj = By.text("Allow")
 *    val dialog = device.wait(Until.findObject(obj), TIMEOUT)
 *    dialog?.let {
 *        it.click()
 *        device.wait(Until.gone(obj), 5_000)
 *    }
 *    ```
 * - or (preferred) executing the grant command on the target package.
 */
fun MacrobenchmarkScope.allowNotificationsViaADB() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val command = "pm grant $packageName ${permission.POST_NOTIFICATIONS}"
        val output = device.executeShellCommand(command)
        Assert.assertEquals("", output)
    }
}

fun MacrobenchmarkScope.allowLocationViaADB() {
    val command = "pm grant $packageName ${permission.ACCESS_FINE_LOCATION}"
    val output = device.executeShellCommand(command)
    Assert.assertEquals("", output)
}

fun MacrobenchmarkScope.deletePreferencesViaADB() {
    val command = "pm clear $packageName"
    val output = device.executeShellCommand(command)
    Assert.assertEquals("Success", output)
}

fun MacrobenchmarkScope.allowNotificationsViaUI() {
    device.waitAndFind(By.text("Allow")).click()
}

fun MacrobenchmarkScope.allowLocationViaUI() {
    device.waitAndFind(By.text("While using the app")).click()
}
