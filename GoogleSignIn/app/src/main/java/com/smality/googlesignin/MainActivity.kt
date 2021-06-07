package com.smality.googlesignin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.main.*

class MainActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        //Telefonda kullanılan gmail hesabına bağlantı sağlanması
        //requestIdToken'da olusturduğumuz Client Id'yı ekledim
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("445789057060-rme187p1gkhc8c49ii9j2rj7bpbbean9.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        //Buttona tıklama eventinde hazırlananan signIn metodunu çağırdık
        google_login_btn.setOnClickListener {
            signIn()
        }
    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
                signInIntent, RC_SIGN_IN
        )
    }
    //Başarılı kimlik doğrulamasından sonra mail hesap bilgilerini alma
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            //Kimlik doğrulama kontrolü
            if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                getSignInResult(task)
        }
    }
    //Google kullanıcısının bilgilerini getiren metod
    private fun getSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                    ApiException::class.java
            )
            // Signed in successfully
            val googleId = account?.id ?: ""
            Log.i("Google ID",googleId)

            val googleFirstName = account?.givenName ?: ""
            Log.i("Google First Name", googleFirstName)

            val googleLastName = account?.familyName ?: ""
            Log.i("Google Last Name", googleLastName)

            val googleEmail = account?.email ?: ""
            Log.i("Google Email", googleEmail)

            val googleProfilePicURL = account?.photoUrl.toString()

            val googleIdToken = account?.idToken ?: ""
            Log.i("Google ID Token", googleIdToken)


            val myIntent = Intent(this, DetailsActivity::class.java)
            myIntent.putExtra("google_id", googleId)
            myIntent.putExtra("google_first_name", googleFirstName)
            myIntent.putExtra("google_last_name", googleLastName)
            myIntent.putExtra("google_email", googleEmail)
            myIntent.putExtra("google_profile_pic_url", googleProfilePicURL)
            myIntent.putExtra("google_id_token", googleIdToken)
            this.startActivity(myIntent)
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                    "failed code=", e.statusCode.toString()
            )
        }
    }
    //Kullanıcının logOut(çıkış) yapmasını sağlayan metod
    private fun signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    // Update your UI here
                }
    }

}
