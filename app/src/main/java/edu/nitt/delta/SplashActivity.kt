package edu.nitt.delta

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.delta.databinding.ActivitySplashBinding
import edu.nitt.delta.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivitySplashBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    startNextActivity()
  }

  private fun startNextActivity() {

    val intent = Intent(this, MainActivity::class.java)
    // for now opening next activity after 2000ms.
    Handler().postDelayed({
      startActivity(intent)
      finish()
      overridePendingTransition(0, 0)
    }, 2000)
  }
}
