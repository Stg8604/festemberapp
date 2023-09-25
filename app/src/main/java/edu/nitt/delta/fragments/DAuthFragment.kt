package edu.nitt.delta.fragments

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.nitt.delta.core.BaseApplication
import edu.nitt.delta.core.profile.DauthConfig
import edu.nitt.delta.core.profile.ProfileAction
import edu.nitt.delta.core.profile.ProfileViewModel
import edu.nitt.delta.databinding.DauthFragmentBinding
import edu.nitt.delta.helpers.viewLifecycle
import edu.nitt.delta.showSnackbar

class DAuthFragment : Fragment() {

  private var binding by viewLifecycle<DauthFragmentBinding>()
  lateinit var profileViewModel: ProfileViewModel
  val TAG = "DAuthFragment"

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DauthFragmentBinding.inflate(inflater, container, false)

    val url = Uri.Builder().apply {
      scheme("https")
      authority("auth.delta.nitt.edu")
      appendPath("authorize")
      appendQueryParameter("client_id", DauthConfig.ClientID.param)
      appendQueryParameter("redirect_uri", DauthConfig.RedirectUri.param)
      appendQueryParameter("response_type", "code")
      appendQueryParameter("grant_type", "authorization_code")
      appendQueryParameter("state", "code")
      appendQueryParameter("scope", "email+openid+profile+user")
      appendQueryParameter("nonce", "")
    }.toString()

    binding.dAuthWebView.webViewClient = object : WebViewClient() {
      @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
      override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
      ): Boolean {
        Log.d("Login", request?.url.toString())
        if (request?.url.toString().contains(DauthConfig.RedirectUri.param)) {
          Log.d("Login", "In web review")
          val code = request?.url?.getQueryParameter("code").toString()
          Log.d("Loginn", code)
          profileViewModel.doAction(ProfileAction.DAuthLoginUser(code))
          return true
        }
        return super.shouldOverrideUrlLoading(view, request)
      }
    }

    binding.dAuthWebView.settings.javaScriptEnabled = true
    binding.dAuthWebView.settings.allowContentAccess = true
    binding.dAuthWebView.settings.domStorageEnabled = true
    binding.dAuthWebView.loadUrl(url)

    observeProfileViewModel()

    return binding.root
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    val factory = (activity?.application as BaseApplication).applicationComponent.getViewModelProviderFactory()
    profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  private fun observeProfileViewModel() {
    profileViewModel.success.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "LoginSuccess")
        showSnackbar(it)
//        profileViewModel.doAction(ProfileAction.RegisterFCM)
        findNavController().navigate(DAuthFragmentDirections.actionDAuthFragmentToHomeFragment())
      })

    profileViewModel.error.observe(viewLifecycleOwner,
      Observer {
        Log.v(TAG, "LoginFailure")
        showSnackbar(it)
      })
  }
}
