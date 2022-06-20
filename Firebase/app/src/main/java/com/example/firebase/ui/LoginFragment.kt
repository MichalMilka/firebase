package com.example.firebase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.firebase.R

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loginText = view.findViewById<EditText>(R.id.LoginInput);
        var passwordText = view.findViewById<EditText>(R.id.PasswordInput);

        view.findViewById<Button>(R.id.ConfirmLoginButton).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
        }
    }
}