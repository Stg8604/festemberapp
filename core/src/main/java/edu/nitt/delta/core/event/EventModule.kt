package edu.nitt.delta.core.event

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.nitt.delta.core.viewmodel.ViewModelKey

@Module
abstract class EventModule {

  @Binds
  @IntoMap
  @ViewModelKey(EventViewModel::class)
  abstract fun bindEventViewModel(eventViewModel: EventViewModel): ViewModel
}
