package com.example.googlesiginsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.googlesiginsample.databinding.ActivityUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.squareup.picasso.Picasso

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = intent.getParcelableExtra<GoogleSignInAccount>("account")

        Picasso.get().load(account?.photoUrl).transform(CircleImage()).into(binding.ivUser)

        with(binding) {
            tvId.text = account?.id
            tvName.text = account?.displayName
            tvEmail.text = account?.email
            btSignOut.setOnClickListener { siginout() }
        }
    }

    private fun siginout() {

    }
}