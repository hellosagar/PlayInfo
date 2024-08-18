package com.example.benchmark

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.SearchCondition
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import java.io.ByteArrayOutputStream

const val TARGET_PACKAGE = "dev.sagar.playinfo"
const val BENCHMARK_ITERATIONS = 15
const val FIVE_SECOND = 5000L
const val TWO_SECOND = 2000L

/**
 * Finds an element by [selector].
 * If not found, fails with [AssertionError] and dumps the window hierarchy.
 */
fun UiDevice.findOrFail(
  selector: BySelector,
  message: String? = null,
): UiObject2 {
  val element = findObject(selector)
  if (element == null) {
    val hierarchy = getWindowHierarchy()
    Log.d("Benchmark", hierarchy)
    throw AssertionError((message ?: "Object not on screen ($selector)") + "\n$hierarchy")
  }
  return element
}

/**
 * Waits until a [searchCondition] evaluates to true.
 * If not found within [timeout], fails with [AssertionError] and dumps the window hierarchy.
 * For example, wait [Until.hasObject] to wait until an element is present on screen.
 */
fun UiDevice.waitOrFail(
  searchCondition: SearchCondition<Boolean>,
  timeout: Long,
  message: String? = null,
) {
  if (!wait(searchCondition, timeout)) {
    val hierarchy = getWindowHierarchy()
    Log.d("Benchmark", hierarchy)
    throw AssertionError((message ?: "Object not on screen") + "\n$hierarchy")
  }
}

/**
 * Combines waiting for an element and returning it.
 * If an object is not present, it throws [AssertionError] and dumps the window hierarchy.
 */
fun UiDevice.waitAndFind(
  selector: BySelector,
  timeout: Long = 5_000,
  message: String? = null,
): UiObject2 {
  waitOrFail(Until.hasObject(selector), timeout, message)
  return findOrFail(selector, message)
}

/**
 * Simplifies dumping window hierarchy
 */
fun UiDevice.getWindowHierarchy(): String {
  val output = ByteArrayOutputStream()
  dumpWindowHierarchy(output)
  return output.toString()
}

fun getIntent(uriString: String): Intent = Intent(Intent.ACTION_VIEW).apply {
  data = Uri.parse(uriString)
  addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed for starting an activity from outside an Activity context
}

fun scrollToText(text: String): Boolean {
  val uiScrollable = UiScrollable(UiSelector().scrollable(true))
  uiScrollable.setAsVerticalList() // Use setAsHorizontalList() for horizontal scrolling

  return try {
    uiScrollable.scrollTextIntoView(text)
  } catch (e: UiObjectNotFoundException) {
    println("Exception during scrolling: ${e.message}")
    false
  }
}

@Suppress("LongMethod", "MagicNumber")
fun safeFlingDownUp(device: UiDevice, flingCount: Int) {
  val displayWidth = device.displayWidth
  val displayHeight = device.displayHeight

  // Calculate 20% margin for all sides
  val marginWidth = (displayWidth * 0.2).toInt()
  val marginHeight = (displayHeight * 0.2).toInt()

  // Calculate the center for swipes
  val centerX = displayWidth / 2

  // Calculate start and end points for the swipe down (bottom of the screen)
  val startYForDown = marginHeight // Start with a 20% margin from the top
  val endYForDown = displayHeight - marginHeight // End with a 20% margin from the bottom

  // Calculate start and end points for the swipe up (top of the screen)
  val startYForUp = displayHeight - marginHeight // Start from the bottom with a 20% margin
  val endYForUp = marginHeight // End at the top with a 20% margin

  // Perform the swipe down to scroll to the bottom
  repeat(flingCount) {
    device.swipe(
      centerX,
      endYForDown,
      centerX,
      startYForDown,
      50,
    ) // Adjust speed/step count as needed
  }

  // Wait for any animations or loading
  device.waitForIdle()

  // Perform the swipe up to scroll back to the top
  repeat(flingCount) {
    device.swipe(
      centerX,
      startYForDown,
      centerX,
      endYForDown,
      50,
    ) // Adjust speed/step count as needed
  }

  // Wait for any animations or loading to complete
  device.waitForIdle()
}
