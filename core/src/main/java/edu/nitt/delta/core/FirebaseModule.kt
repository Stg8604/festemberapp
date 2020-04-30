package edu.nitt.delta.core

import com.crashlytics.android.Crashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideCrashlyticsInstance(): Crashlytics {
        val crashlytics: Crashlytics = Crashlytics.getInstance()
        /*
        * Set Custom Keys here
        * */
        return crashlytics
    }
}
