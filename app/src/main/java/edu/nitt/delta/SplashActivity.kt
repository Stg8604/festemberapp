package edu.nitt.delta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.event.EventAction
import edu.nitt.delta.core.event.EventViewModel
import edu.nitt.delta.databinding.ActivitySplashBinding
import edu.nitt.delta.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivitySplashBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

  override fun onStart() {
    super.onStart()

    val factory = (application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    val eventViewModel = ViewModelProvider(this, factory).get(EventViewModel::class.java)
    eventViewModel.doAction(EventAction.UpdateEvents)

    observeAndStartNextActivity(eventViewModel)
  }

  private fun observeAndStartNextActivity(eventViewModel: EventViewModel) {

    val intent = Intent(this, MainActivity::class.java)

    eventViewModel.success.observe(this,
      Observer {
        // Next activity is launched once event data is updated.
        startActivity(intent)
        finish()
        overridePendingTransition(0, 0)
      })

    eventViewModel.error.observe(this,
      Observer {
        showSnackbar("Something went wrong... $it")
      })
  }
}
