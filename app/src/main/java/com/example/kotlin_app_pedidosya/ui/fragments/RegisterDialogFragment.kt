package com.example.kotlin_app_pedidosya.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_app_pedidosya.data.SessionManager
import com.example.kotlin_app_pedidosya.databinding.FragmentRegisterDialogBinding
import com.example.kotlin_app_pedidosya.ui.activities.ProductListActivity
import com.example.kotlin_app_pedidosya.ui.utils.TextChangedListener
import com.example.kotlin_app_pedidosya.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

fun EditText.bindToViewModel(viewModel: RegisterViewModel) {
    this.addTextChangedListener(object : TextChangedListener<EditText>(this) {
        override fun onTextChanged(target: EditText, s: Editable?) {
            val key = target.tag as? String ?: return
            viewModel.updateRegisterField(key, s.toString())
        }
    })
}

class RegisterDialogFragment : BottomSheetDialogFragment() {
    private val registerViewModel: RegisterViewModel by viewModels()

    private var _binding: FragmentRegisterDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val session = SessionManager(requireContext())

        val inputEmail = binding.email
        val inputEmailError = binding.emailError

        val inputPassword = binding.password
        val inputPasswordError = binding.passwordError

        val inputFullName = binding.fullName
        val inputFullNameError = binding.fullNameError

        val inputRepeatPassword = binding.repeatPassword
        val inputRepeatPasswordError = binding.repeatPasswordError

        val btnRegister = binding.btnRegister

        inputFullName.bindToViewModel(registerViewModel)
        inputEmail.bindToViewModel(registerViewModel)
        inputPassword.bindToViewModel(registerViewModel)
        inputRepeatPassword.bindToViewModel(registerViewModel)

        inputFullName.setOnFocusChangeListener { _, hasFocus ->
            inputFullNameError.text = if (!hasFocus) registerViewModel.errorMsg.value.fullName else ""
        }

        inputEmail.setOnFocusChangeListener { _, hasFocus ->
            inputEmailError.text = if (!hasFocus) registerViewModel.errorMsg.value.email else ""
        }

        inputPassword.setOnFocusChangeListener { _, hasFocus ->
            inputPasswordError.text =
                if (!hasFocus) registerViewModel.errorMsg.value.password else ""
        }

        inputRepeatPassword.setOnFocusChangeListener { _, hasFocus ->
            inputRepeatPasswordError.text =
                if (!hasFocus) registerViewModel.errorMsg.value.repeatPassword else ""
        }

        btnRegister.setOnClickListener {
            session.setLogged(true)

            val intent = Intent(requireActivity(), ProductListActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        lifecycleScope.launch {
            registerViewModel.register.collect {
                val isValidData = registerViewModel.isValidateData()
                btnRegister.isEnabled = isValidData

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}