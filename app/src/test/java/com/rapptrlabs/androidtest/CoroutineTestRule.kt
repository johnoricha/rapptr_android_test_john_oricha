package com.rapptrlabs.androidtest

import kotlinx.coroutines.CoroutineDispatcher
import org.junit.rules.TestWatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val dispatcher: CoroutineDispatcher = StandardTestDispatcher()) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}