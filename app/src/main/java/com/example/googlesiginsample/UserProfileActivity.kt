package com.example.googlesiginsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.example.googlesiginsample.databinding.ActivityUserProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        Picasso.get().load(account?.photoUrl).transform(CircleImage()).into(binding.ivUser)

        with(binding) {
            tvId.text = account?.id
            tvName.text = account?.displayName
            tvEmail.text = account?.email
            btSignOut.setOnClickListener { siginout() }
        }
    }

    private fun siginout() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        GoogleSignIn.getClient(this, gso)
            .signOut()
            .addOnCompleteListener {
                finish()
                makeText(this@UserProfileActivity, "Signed out successfully !!!", LENGTH_SHORT).show()
            }
    }
}