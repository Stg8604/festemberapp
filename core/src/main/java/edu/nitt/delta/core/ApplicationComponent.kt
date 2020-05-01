package edu.nitt.delta.core

import android.app.Application
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import edu.nitt.delta.core.api.FestApiInterface
import edu.nitt.delta.core.api.FestApiModule
import edu.nitt.delta.core.api.Routes
import edu.nitt.delta.core.storage.SharedPrefHelper
import edu.nitt.delta.core.storage.StorageModule
import javax.inject.Named
import javax.inject.Singleton

@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    FestApiModule::class,
    FirebaseModule::class,
    StorageModule::class
  ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<BaseApplication> {

  fun getFestApi(): FestApiInterface
  fun getGson(): Gson

  fun getSharedPrefManager(): SharedPrefHelper

  @Component.Factory
  interface Factory {

    fun create(
      @BindsInstance
      application: Application,

      @BindsInstance
      @Named("url")
      url: String = Routes.BASE_URL
    ): ApplicationComponent
  }
}
