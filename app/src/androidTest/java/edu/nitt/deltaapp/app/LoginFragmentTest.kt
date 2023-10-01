package edu.nitt.deltaapp.app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.nitt.deltaapp.R
import edu.nitt.deltaapp.fragments.LoginFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginFragmentTest {

  @Test
  fun textTest() {
    val scenario = launchFragmentInContainer<LoginFragment>()
    onView(withId(R.id.btnLogin)).check(matches(withText("Login")))
  }
}
