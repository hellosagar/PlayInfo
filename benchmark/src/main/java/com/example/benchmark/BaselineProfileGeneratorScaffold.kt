package com.example.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A scaffold for creating a baseline profile user journey. Implementing classes can
 * start generating a profile directly by implementing [MacrobenchmarkScope.profileBlock].
 */
@RunWith(AndroidJUnit4::class)
abstract class BaselineProfileGeneratorScaffold {

  @get:Rule
  open val rule = BaselineProfileRule()

  open val includeInStartupProfile: Boolean = false

  abstract fun MacrobenchmarkScope.profileBlock()

  @Test
  fun profileGenerator() {
    rule.collect(
      includeInStartupProfile = includeInStartupProfile,
      packageName = TARGET_PACKAGE,
      maxIterations = 1,
    ) {
      profileBlock()
    }
  }
}
