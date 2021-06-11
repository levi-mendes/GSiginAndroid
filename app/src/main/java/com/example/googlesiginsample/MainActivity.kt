package com.example.googlesiginsample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.googlesiginsample.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException

private const val RC_SIGN_IN: Int = 10

class MainActivity : AppCompatActivity() {

    private lateinit var siginClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        siginClient = GoogleSignIn.getClient(this, gso)

        configButton()

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            callProfile()
        }
    }

    private fun configButton() {
        binding.btAutenticar.apply {
            setSize(SignInButton.SIZE_WIDE)
            setOnClickListener {
                autenticar()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                if (task != null && account != null) {
                    callProfile()
                }
            }
        }
    }

//    private var resultConnect: ActivityResultLauncher<Intent> =
//        registerForActivityResult(StartActivityForResult()) { result ->
//
//        if (result.resultCode == Activity.RESULT_OK) {
//
//            //if (result.requestCode == RC_SIGN_IN) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//                val account = task.getResult(ApiException::class.java)
//                callProfile(account)
//            //}
//        }
//    }

    private fun callProfile() {
        startActivity(Intent(this, UserProfileActivity::class.java))
    }

    private fun autenticar() {
        val siginIntent = siginClient.signInIntent
        startActivityForResult(siginIntent, RC_SIGN_IN)
    }
}