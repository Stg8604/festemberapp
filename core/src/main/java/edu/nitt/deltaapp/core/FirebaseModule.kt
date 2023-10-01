package edu.nitt.deltaapp.core

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideCrashlyticsInstance(): FirebaseCrashlytics {
        val crashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
        /*
        * Set Custom Keys here
        * */
        return crashlytics
    }
}
