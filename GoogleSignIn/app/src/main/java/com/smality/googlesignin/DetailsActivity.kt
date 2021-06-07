package com.smality.googlesignin


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val googleFirstName = intent.getStringExtra("google_first_name")
        val googleLastName = intent.getStringExtra("google_last_name")
        val googleEmail = intent.getStringExtra("google_email")
        val googleProfilePicURL = intent.getStringExtra("google_profile_pic_url")

        Picasso.get().load(googleProfilePicURL) .resize(500, 500).into(profil_pic);
        google_name_textview.text = googleFirstName +" "+ googleLastName
        google_email_textview.text = googleEmail
    }
}
