package com.example.googlesiginsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.example.googlesiginsample.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

class MainActivity : AppCompatActivity() {

    private lateinit var siginClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configGoogleSigin()

        configButton()

        val account = GoogleSignIn.getLastSignedInAccount(this)

        account?.let {
            callProfile()
        }
    }

    private fun configGoogleSigin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        siginClient = GoogleSignIn.getClient(this, gso)
    }

    private fun configButton() {
        binding.btAutenticar.apply {
            setSize(SignInButton.SIZE_WIDE)
            setOnClickListener {
                siginResultConnect.launch(siginClient.signInIntent)
            }
        }
    }

    private fun callProfile() = startActivity(Intent(this, UserProfileActivity::class.java))

    private val siginResultConnect: ActivityResultLauncher<Intent> =
        registerForActivityResult(StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            callProfile()
        }
    }
}