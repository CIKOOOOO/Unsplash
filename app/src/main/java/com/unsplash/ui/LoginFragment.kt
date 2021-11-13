package com.unsplash.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.unsplash.R
import com.unsplash.databinding.FragmentLoginBinding
import com.unsplash.databinding.FragmentUnsplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnClickListener {

            val userName = binding.tetUsername.text.toString()
            val password = binding.tetPassword.text.toString()
            if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                login(userName, password)
            } else {
                Toast.makeText(requireActivity(),"Username atau Password tidak boleh kosong"
                    ,Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun login(userName: String, password: String){
        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(requireActivity()).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
            Log.d("GLG", "error" + exception.message)
        }

        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch() {

            val status = loginViewModel.login(userName,password)
            if (status == "success") {
                goToListPage()
            } else {
                AlertDialog.Builder(requireActivity()).setTitle("Error")
                    .setMessage(status)
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }

        }

    }

    fun goToListPage() {
        val action = LoginFragmentDirections.actionLoginFragmentToUnsplashFragment()
        findNavController().navigate(action)
    }

}