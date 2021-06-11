package com.example.googlesiginsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.googlesiginsample.databinding.FragmentSiginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class SiginFragment : BaseFragment() {

    private lateinit var siginClient: GoogleSignInClient
    private var binding: FragmentSiginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSiginBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configGoogleSigin()

        configButton()

        val account = GoogleSignIn.getLastSignedInAccount(activity)

        account?.let {
            callProfile()
        }
    }

    private fun configGoogleSigin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        siginClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun configButton() {
        binding!!.btAutenticar.apply {
            setSize(SignInButton.SIZE_WIDE)
            setOnClickListener {
                siginResultConnect.launch(siginClient.signInIntent)
            }
        }
    }

    private fun callProfile(){
        findNavController().navigate(R.id.userProfileFragment)
    }

    private val siginResultConnect: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {
                callProfile()
            }
        }
}