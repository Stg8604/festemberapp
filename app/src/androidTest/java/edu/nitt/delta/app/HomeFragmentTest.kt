package edu.nitt.delta.app

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import edu.nitt.delta.R
import edu.nitt.delta.fragments.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

  @Test
  fun textTest() {
    val scenario = launchFragmentInContainer<HomeFragment>()
    onView(withId(R.id.btnLogout)).check(matches(withText("Logout")))
    onView(withId(R.id.tvHome)).check(matches(withText("Home")))
  }
}
