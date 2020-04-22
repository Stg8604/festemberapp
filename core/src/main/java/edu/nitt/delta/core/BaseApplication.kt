package edu.nitt.delta.core

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import edu.nitt.delta.core.api.Routes

class BaseApplication : DaggerApplication() {
  override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
    return DaggerApplicationComponent.factory()
      .create(this)
  }
}
