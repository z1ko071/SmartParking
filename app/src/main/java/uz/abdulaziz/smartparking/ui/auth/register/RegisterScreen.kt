package uz.abdulaziz.smartparking.ui.auth.register

import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.utils.gone
import uz.abdulaziz.smartparking.core.utils.visible
import uz.abdulaziz.smartparking.databinding.ScreenRegisterBinding


class RegisterScreen : BaseFragment(R.layout.screen_register) {

    private val binding by viewBinding(ScreenRegisterBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated() {

        loadActions()
        observe()

    }

    private fun observe() {

        viewModel.successLD.observe(viewLifecycleOwner) {
            binding.loader.gone()
            binding.signupBtn.isVisible = true
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToLoginScreen())
        }
        viewModel.errorLD.observe(viewLifecycleOwner) {
            binding.loader.gone()
            binding.signupBtn.isVisible = true
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }


    }

    private fun loadActions() {

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(RegisterScreenDirections.actionRegisterScreenToLoginScreen())
        }

        binding.signupBtn.setOnClickListener {

            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            val confirmPassword = binding.comfirmPasswordEdit.text.toString()

            if (email.isBlank()) {
                binding.emailEdit.error = "Enter"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordEdit.error = "Enter"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.comfirmPasswordEdit.error = "Enter"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(context, "Not match password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.loader.visible()
            binding.signupBtn.isInvisible = false
            viewModel.register(LoginRequest(email, password))

        }

    }


}