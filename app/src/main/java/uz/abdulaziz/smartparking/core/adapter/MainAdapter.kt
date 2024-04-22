package uz.abdulaziz.smartparking.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.abdulaziz.smartparking.ui.main.location.LocationPage
import uz.abdulaziz.smartparking.ui.main.profile.ProfilePage

class MainAdapter(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LocationPage()
            else -> ProfilePage()
        }
    }


}