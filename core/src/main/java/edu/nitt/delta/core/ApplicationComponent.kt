package edu.nitt.delta.core

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import edu.nitt.delta.core.database.DbModule
import javax.inject.Singleton

@Component(
  modules = [
    AndroidSupportInjectionModule::class,
    DbModule::class
  ]
)
@Singleton
interface ApplicationComponent : AndroidInjector<BaseApplication> {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): ApplicationComponent
  }
}
