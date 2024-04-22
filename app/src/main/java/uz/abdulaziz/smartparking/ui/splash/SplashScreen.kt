package uz.abdulaziz.smartparking.ui.splash

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.utils.setItemStatusBarColor
import uz.abdulaziz.smartparking.databinding.ScreenSplashBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseFragment(R.layout.screen_splash) {

    private val binding by viewBinding(ScreenSplashBinding::bind)
    private lateinit var localStorage: LocalStorage

    override fun onViewCreated() {

        binding.loader.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_anim))

        setItemStatusBarColor(Color.parseColor("#7784FF"), true)

        localStorage = LocalStorage(requireContext())


        lifecycleScope.launch {
            delay(3000)
            if (localStorage.onBoarding) {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToBoardingScreen())
            } else if (localStorage.isFirst && !localStorage.onBoarding) {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginScreen())
            } else {
                findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
            }

        }


    }


}