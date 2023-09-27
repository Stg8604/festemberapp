package edu.nitt.delta.fragments
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.jinatonic.confetti.CommonConfetti
import edu.nitt.delta.databinding.HomeFragmentBinding
import edu.nitt.delta.helpers.viewLifecycle

class HomeFragment : Fragment() {
  private var binding by viewLifecycle<HomeFragmentBinding>()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = HomeFragmentBinding.inflate(inflater, container, false)
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    val elemList = arrayOf(
      binding.NitTrichyLanding,
      binding.festemberTextLanding,
      binding.themeLanding,
      binding.registerLanding
    )
    elemList[0].textSize = screenWidth.toFloat() / 40
    elemList[1].textSize = screenWidth.toFloat() / 20
    elemList[2].textSize = screenWidth.toFloat() / 45
    elemList[3].textSize = screenWidth.toFloat() / 50
    binding.landingSocialsOpener.layoutParams.height = (screenHeight / 5.5).toInt()
    binding.landingSocialsOpener.layoutParams.width = (screenWidth / 3.5).toInt()

    val iconList = arrayOf(
      binding.messageIcon,
      binding.calenderIcon,
      binding.mediumIcon,
      binding.linkedinIcon,
      binding.instagramIcon,
      binding.youtubeIcon,
      binding.twitterIcon,
      binding.facebookIcon
    )
    for (i in 0..7) {
      iconList[i].layoutParams.height = (screenWidth / 6.5).toInt()
      iconList[i].layoutParams.width = (screenWidth / 6.5).toInt()
    }
    return binding.root
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val eleList = arrayOf(
      binding.NitTrichyLanding,
      binding.festemberTextLanding,
      binding.themeLanding,
      binding.registerLanding
    )
    for (i in 0..3) {
      val postSec = 1300
      Looper.myLooper()?.let {
        Handler(it).postDelayed({
          eleList[i].isVisible = true
        }, (postSec * i).toLong())
      }
    }
    binding.topBarBinding.festemberLogo.setOnClickListener {
      CommonConfetti.rainingConfetti(binding.root, intArrayOf(Color.parseColor("#BA465A")))
        .stream(1690)
    }
    fun click() {
    binding.landingSocialsOpener.setOnClickListener {
      val initialTime = 125
      val redPanda = binding.landingSocialsOpener
      val iconArray = arrayOf(
        binding.messageIcon,
        binding.calenderIcon,
        binding.mediumIcon,
        binding.linkedinIcon,
        binding.instagramIcon,
        binding.youtubeIcon,
        binding.twitterIcon,
        binding.facebookIcon
      )
      val socialLinks = arrayOf(
        "https://addmessagelink",
        "https://addcalendarlink",
        "https://medium.com/@TeamContent_Festember",
        "https://www.linkedin.com/company/festember/mycompany/",
        "https://www.instagram.com/festember/",
        "https://www.youtube.com/@FestemberNITTrichy",
        "https://twitter.com/festember",
        "https://www.facebook.com/festember"
      )
      redPanda.isEnabled = false
      val iconNum = 8
      for (i in 0 until iconNum) {
        Looper.myLooper()?.let { it1 ->
          Handler(it1).postDelayed({
            iconArray[i].isVisible = true
            iconArray[i].translationY = -200F + -180F * i
          }, initialTime.toLong() * i)
        }
      }

      val initial_pos = -0F
      Looper.myLooper()?.let { it1 ->
        Handler(it1).postDelayed({
          redPanda.isEnabled = true
        }, initialTime.toLong() * 7)
      }
      for (j in 0 until iconNum) {
        iconArray[j].setOnClickListener {
          val link = Uri.parse(socialLinks[j])
          val intent = Intent(Intent.ACTION_VIEW, link)
          startActivity(intent)
        }
      }
      redPanda.setOnClickListener {
        redPanda.isEnabled = false
        for (i in 0 until iconNum) {
          Looper.myLooper()?.let { it1 ->
            Handler(it1).postDelayed({
              iconArray[iconNum - 1 - i].translationY = initial_pos
              iconArray[iconNum - 1 - i].isVisible = false
            }, initialTime.toLong() * i)
          }
        }
        Looper.myLooper()?.let { it1 ->
          Handler(it1).postDelayed({
            redPanda.isEnabled = true
          }, initialTime.toLong() * 7)
        }
        click()
      }
    }
    }
    click()
    binding.topBarBinding.Login.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
    }
    binding.registerLanding.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSignUpFragment2())
    }
    binding.navBarButtonBinding.navBarButton.setOnClickListener {
      findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNavBarFragment())
    }
  }
}
