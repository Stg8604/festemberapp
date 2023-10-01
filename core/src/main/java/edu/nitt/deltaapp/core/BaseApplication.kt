package edu.nitt.deltaapp.core

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

  val applicationComponent: ApplicationComponent = DaggerApplicationComponent.factory()
    .create(this)

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return applicationComponent
  }
}
