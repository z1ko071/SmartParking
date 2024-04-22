package uz.abdulaziz.smartparking.ui.edit_profile

import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.abdulaziz.smartparking.R
import uz.abdulaziz.smartparking.core.base.BaseFragment
import uz.abdulaziz.smartparking.core.cache.LocalStorage
import uz.abdulaziz.smartparking.core.model.edit.ProfileUpdateRequest
import uz.abdulaziz.smartparking.core.utils.gone
import uz.abdulaziz.smartparking.core.utils.visible
import uz.abdulaziz.smartparking.databinding.ScreenEditProfileBinding


class EditProfileScreen : BaseFragment(R.layout.screen_edit_profile) {

    private val binding by viewBinding(ScreenEditProfileBinding::bind)
    private val viewModel by viewModels<EditProfileViewModel>()
    private val localStorage by lazy { LocalStorage(requireContext()) }

    override fun onViewCreated() {

        viewModel.getProfileData(localStorage.user_id.toInt())
        observe()
        setListener()

    }

    private fun observe() {
        viewModel.userProfileDataLD.observe(viewLifecycleOwner) {

            binding.fullNameEdit.setText(it.fullName)
            binding.carNumberEdit.setText(it.carNumber)
            binding.birthdayEdit.setText(it.dateOfBirth.toString())
        }

        viewModel.succedData.observe(viewLifecycleOwner) {
            binding.loader.gone()
            binding.finshBtn.isVisible = true
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.loader.gone()
            binding.finshBtn.isVisible = true
            Toast.makeText(context, "Error please retry again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListener() {

        binding.finshBtn.setOnClickListener {

            val full = binding.fullNameEdit.text.toString()
            val date = binding.birthdayEdit.text.toString()
            val carNum = binding.carNumberEdit.text.toString()

            if (full.isBlank()) {
                binding.fullNameEdit.error = "Enter"
                return@setOnClickListener
            }

            if (date.isBlank()) {
                binding.birthdayEdit.error = "YYYY-MM-DD"
                return@setOnClickListener
            }

            if (carNum.isBlank()) {
                binding.carNumberEdit.error = "example: 01 A777AB"
                return@setOnClickListener
            }

            val body = ProfileUpdateRequest(full, date, carNum)

            binding.loader.visible()
            binding.finshBtn.isInvisible = true
            viewModel.updateProfile(localStorage.user_id.toInt(), body)

        }


    }

}

/* private fun prepareImagePart(imagePath: String): MultipartBody.Part? {
    val file = File(imagePath)
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}

private val PICK_IMAGE_REQUEST = 1

private fun openGallery() {
    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    startActivityForResult(intent, PICK_IMAGE_REQUEST)
}

private fun uriToFilePath(uri: Uri): String? {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context?.contentResolver?.query(uri, projection, null, null, null)
    val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val filePath = columnIndex?.let { cursor?.getString(it) }
    cursor?.close()
    return filePath
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
        val selectedImageUri = data.data

        binding.userImage.setImageURI(selectedImageUri)
        imagePath = selectedImageUri?.let { uriToFilePath(it) }
    }
}*/*/

