package com.example.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

private const val NAME = "Sagar Khurana"
private const val PASSWORD = "1234567890"
private fun getEmailId() = "sagarkhurana00786${System.currentTimeMillis()}@gmail.com"

fun MacrobenchmarkScope.startupUntilSignupFlow() {
    device.waitAndFind(By.res("onboarding_signup_button")).click()
    device.waitAndFind(By.res("signup_name_text_field")).text = NAME
    device.waitAndFind(By.res("signup_email_text_field")).text = getEmailId()
    device.waitAndFind(By.res("signup_password_text_field")).text = PASSWORD
    device.waitAndFind(By.res("signup_submit_button"))
}

fun MacrobenchmarkScope.startupHomeToDetailScreenFlow() {
    if (device.wait(Until.hasObject(By.res("onboarding_signup_button")), FIVE_SECOND)) {
        startupUntilSignupFlow()
        device.waitAndFind(By.res("signup_submit_button")).click()
    }
    device.waitForIdle()
    device.wait(Until.hasObject(By.res("home_game_list")), FIVE_SECOND)
    safeFlingDownUp(device, 2)
    Thread.sleep(TWO_SECOND)
    device.waitAndFind(By.res("home_game_item_1")).click()
    device.waitForIdle()
    device.wait(Until.hasObject(By.res("game_detail_image")), FIVE_SECOND)
    device.waitAndFind(By.res("game_detail_image"))
    safeFlingDownUp(device, 1)
}