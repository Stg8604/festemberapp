package edu.nitt.delta.core

import android.app.Application
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.api.FestApiModule
import edu.nitt.delta.core.event.EventModule
import edu.nitt.delta.core.profile.ProfileModule
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.core.storage.StorageModule
import edu.nitt.delta.core.viewmodel.ViewModelProviderFactory
import edu.nitt.delta.core.viewmodel.ViewModelProviderModule
import javax.inject.Named
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
      application: Application,

      @BindsInstance
      @Named("url")
      url: String = BASE_URL
    ): ApplicationComponent
  }
}
