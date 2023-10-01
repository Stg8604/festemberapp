package edu.nitt.deltaapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.deltaapp.databinding.ActivitySplashBinding
import edu.nitt.deltaapp.helpers.viewLifecycle

class SplashActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivitySplashBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }

  override fun onStart() {
    super.onStart()
    val delayMillis = 3500L

    // Delay for 2 seconds (adjust the delay time as needed)
    Handler(Looper.getMainLooper()).postDelayed({
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
      finish()
      overridePendingTransition(0, 0)
    }, delayMillis)
  }
}
