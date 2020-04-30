package edu.nitt.delta

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.nitt.delta.databinding.ActivityMainBinding
import edu.nitt.delta.helpers.viewLifecycle

class MainActivity : AppCompatActivity() {

  private val binding by viewLifecycle(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}
