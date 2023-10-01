package edu.nitt.deltaapp.core

import android.app.Application
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import edu.nitt.deltaapp.core.api.FestApiInterface
import edu.nitt.deltaapp.core.api.FestApiModule
import edu.nitt.deltaapp.core.event.EventModule
import edu.nitt.deltaapp.core.profile.ProfileModule
import edu.nitt.deltaapp.core.storage.SharedPrefHelper
import edu.nitt.deltaapp.core.storage.StorageModule
import edu.nitt.deltaapp.core.viewmodel.ViewModelProviderFactory
import edu.nitt.deltaapp.core.viewmodel.ViewModelProviderModule
import javax.inject.Singleton

@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    FestApiModule::class,
    FirebaseModule::class,
    StorageModule::class,
    ViewModelProviderModule::class,
    EventModule::class,
    ProfileModule::class
  ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<BaseApplication> {
  fun getFestApi(): FestApiInterface
  fun getGson(): Gson

  fun getSharedPrefManager(): SharedPrefHelper

  fun getViewModelProviderFactory(): ViewModelProviderFactory

  @Component.Factory
  interface Factory {

    fun create(
      @BindsInstance
      application: Application
    ): ApplicationComponent
  }
}
