package uz.abdulaziz.smartparking.ui.main

import android.graphics.Color
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.adapter.MainAdapter
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.utils.setItemStatusBarColor
import uz.abdulaziz.smartparking.databinding.ScreenMainBinding


class MainScreen : BaseFragment(R.layout.screen_main) {

    private val binding by viewBinding(ScreenMainBinding::bind)

    override fun onViewCreated() {
        setItemStatusBarColor(Color.parseColor("#4D5DFA"), false)

        setViewPager()

    }

    private fun setViewPager() {

        val adapter = MainAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigaion.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_menu -> binding.viewPager.setCurrentItem(0, false)
                // R.id.saved_menu -> binding.viewPager.setCurrentItem(1, false)
                R.id.profile_menu -> binding.viewPager.setCurrentItem(1, false)
            }

            return@setOnItemSelectedListener true
        }


    }


}