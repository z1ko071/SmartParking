package uz.abdulaziz.smartparking.ui.main.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.databinding.PageProfileBinding
import uz.abdulaziz.smartparking.ui.dialog.LogOutDialog
import uz.abdulaziz.smartparking.ui.main.MainScreenDirections

class ProfilePage : BaseFragment(R.layout.page_profile) {

    private val binding by viewBinding(PageProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var localStorage: LocalStorage

    override fun onViewCreated() {
        localStorage = LocalStorage(requireContext())
        viewModel.getProfileData(localStorage.user_id.toInt())
        observer()
        loadActions()

    }

    private fun loadActions() {

        binding.logOutBtn.setOnClickListener {
            val dialog = LogOutDialog(requireContext())
            dialog.onYesClick = {
                findNavController().navigate(MainScreenDirections.actionMainScreenToLoginScreen())
                localStorage.isFirst = true
                localStorage.user_id = ""
                localStorage.isElectro = "False"
                localStorage.isParking = false
                localStorage.parkingId = ""

                localStorage.cardHolder = ""
                localStorage.cardNum = ""
                localStorage.cardDate = ""
                localStorage.cardCVV = ""
            }
            dialog.show()
        }
        binding.editProfileBtn.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToSetProfileScreen())
        }
        binding.paymentBtn.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToPayScreen())
        }

    }

    private fun observer() {
        viewModel.userProfileDataLD.observe(viewLifecycleOwner) {

            binding.userImage.load("https://parling.salikhdev.uz/${it.image}") {
                error(R.drawable.ic_person_holder)
                placeholder(R.drawable.ic_person_holder)
            }
            binding.userName.text = it.fullName
            binding.userEmail.text = it.email

        }
    }


}