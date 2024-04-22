package uz.abdulaziz.smartparking.ui.boarding

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.adapter.BoardingAdapter
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.utils.setItemStatusBarColor
import uz.abdulaziz.smartparking.databinding.ScreenBoardingBinding


class BoardingScreen : BaseFragment(R.layout.screen_boarding) {

    private val binding by viewBinding(ScreenBoardingBinding::bind)
    private val adapter by lazy { BoardingAdapter() }
    private lateinit var localStorage: LocalStorage

    override fun onViewCreated() {
        setItemStatusBarColor(Color.parseColor("#4D5DFA"), false)
        localStorage = LocalStorage(requireContext())
        loadAdapter()
        loadActions()

    }

    private fun loadActions() {

        binding.nextBtn.setOnClickListener {
            val current = binding.viewPager.currentItem
            if (current != 2) {
                binding.viewPager.currentItem = (current + 1)
            } else {
                localStorage.onBoarding = false
                findNavController().navigate(BoardingScreenDirections.actionBoardingScreenToLoginScreen())
            }
        }

    }

    private fun loadAdapter() {
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(viewPager2 = binding.viewPager)
    }


}