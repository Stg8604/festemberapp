package edu.nitt.delta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.databinding.ActivityMainBinding
import edu.nitt.delta.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

  override fun onStart() {
    super.onStart()

    val factory = (application as BaseApplication).applicationComponent.getViewModelProviderFactory()

    val eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)

    observeEventViewModel(eventViewModel)
  }

  private fun observeEventViewModel(eventViewModel: EventViewModel) {
    eventViewModel.success.observe(this,
      Observer {
        showSnackbar("YAY!! $it")
      })

    eventViewModel.error.observe(this,
      Observer {
        showSnackbar("OOPS!! $it")
      })
  }
}
