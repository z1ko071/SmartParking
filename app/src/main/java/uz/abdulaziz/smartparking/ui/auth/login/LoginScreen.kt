package uz.abdulaziz.smartparking.ui.auth.login

import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.model.login.LoginRequest
import uz.abdulaziz.smartparking.core.utils.gone
import uz.abdulaziz.smartparking.core.utils.visible
import uz.abdulaziz.smartparking.databinding.ScreenLoginBinding


class LoginScreen : BaseFragment(R.layout.screen_login) {

    private val binding by viewBinding(ScreenLoginBinding::bind)
    private lateinit var localStorage: LocalStorage
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated() {
        localStorage = LocalStorage(requireContext())
        loadAction()
        observe()

    }

    private fun observe() {

        viewModel.successLD.observe(viewLifecycleOwner) {
            localStorage.isFirst = false
            localStorage.user_id = it.id.toString()
            binding.loader.gone()
            binding.loginBtn.isVisible = true
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToMainScreen())
        }

        viewModel.errorLD.observe(viewLifecycleOwner) {
            binding.loader.gone()
            binding.loginBtn.isVisible = true
            when (it) {
                401 -> Toast.makeText(context, "Email or Password not true", Toast.LENGTH_SHORT)
                    .show()

                800 -> Toast.makeText(context, "Internet error", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loadAction() {

        binding.loginBtn.setOnClickListener {

            if (binding.emailEdit.text.toString().isBlank()) {
                binding.emailEdit.error = "Enter"
                return@setOnClickListener
            }

            if (binding.passwordEdit.text.toString().isBlank()) {
                binding.passwordEdit.error = "Enter"
                return@setOnClickListener
            }

            if (!isValidEmail(email = binding.emailEdit.text.toString())) {
                binding.emailEdit.error = "Email Format"
                return@setOnClickListener
            }

            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString()

            binding.loader.visible()
            binding.loginBtn.isInvisible = true
            viewModel.login(LoginRequest(email, password))
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(LoginScreenDirections.actionLoginScreenToRegisterScreen())
        }

    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}