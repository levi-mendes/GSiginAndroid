package com.example.googlesiginsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.googlesiginsample.databinding.FragmentUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class UserProfileFragment : BaseFragment() {

    /*
    TODO

     - criar viewmodel e receber  GoogleSignIn ou repositorio
     - carregar os campos atraves do databinging usando um objeto do viewmodel
     - criar testes unitatios e integracao / aceitacao
     */

    private var binding: FragmentUserProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        Picasso.get().load(account?.photoUrl).transform(CircleImage()).into(binding?.ivUser)

        with(binding!!) {
            tvId.text = account?.id
            tvName.text = account?.displayName
            tvEmail.text = account?.email
            btSignOut.setOnClickListener { siginout() }
        }
    }

    private fun siginout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()

        GoogleSignIn.getClient(requireActivity(), gso)
            .signOut()
            .addOnCompleteListener {
                findNavController().popBackStack()
                showToast(getString(R.string.msg_signedout))
            }
    }
}