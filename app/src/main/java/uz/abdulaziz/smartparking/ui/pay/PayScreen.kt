package uz.abdulaziz.smartparking.ui.pay

import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.utils.CreditCardTextWatcher
import uz.abdulaziz.smartparking.core.utils.ExpiryDateTextWatcher
import uz.abdulaziz.smartparking.databinding.ScreenPayBinding

class PayScreen : BaseFragment(R.layout.screen_pay) {

    private val binding by viewBinding(ScreenPayBinding::bind)
    private val localStorage by lazy { LocalStorage(requireContext()) }


    override fun onViewCreated() {

        setData()
        setEdits()
        loadActions()

    }

    private fun setData() {
        if (localStorage.cardHolder.isNotEmpty()) {
            binding.fullNameEdit.setText(localStorage.cardHolder)
        }
        if (localStorage.cardCVV.isNotEmpty()) {
            binding.cvvEdit.setText(localStorage.cardCVV)
        }
        if (localStorage.cardDate.isNotEmpty()) {
            binding.dateEdit.setText(localStorage.cardDate)
        }
        if (localStorage.cardNum.isNotEmpty()) {
            binding.carNumberEdit.setText(localStorage.cardNum)
        }
    }


    private fun loadActions() {

        binding.finshBtn.setOnClickListener {
            if (binding.fullNameEdit.text.toString().isBlank()) {
                binding.fullNameEdit.error = "Enter"
                return@setOnClickListener
            }
            if (binding.carNumberEdit.text.toString().isBlank()) {
                binding.carNumberEdit.error = "Enter"
                return@setOnClickListener
            }
            if (binding.dateEdit.text.toString().isBlank()) {
                binding.dateEdit.error = "Enter"
                return@setOnClickListener
            }
            if (binding.cvvEdit.text.toString().isBlank()) {
                binding.cvvEdit.error = "Enter"
                return@setOnClickListener
            }
            localStorage.cardHolder = binding.fullNameEdit.text.toString().trim()
            localStorage.cardNum = binding.carNumberEdit.text.toString().trim()
            localStorage.cardCVV = binding.cvvEdit.text.toString().trim()
            localStorage.cardDate = binding.dateEdit.text.toString().trim()
            Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setEdits() {
        binding.carNumberEdit.addTextChangedListener(CreditCardTextWatcher(binding.carNumberEdit))
        binding.dateEdit.addTextChangedListener(ExpiryDateTextWatcher(binding.dateEdit))
    }


}