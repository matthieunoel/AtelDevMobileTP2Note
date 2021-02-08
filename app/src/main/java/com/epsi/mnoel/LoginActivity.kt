package com.epsi.mnoel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Firebase.initialize(application)




        val submitBtn = findViewById<Button>(R.id.btn_submit)

        submitBtn.setOnClickListener {
//            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            /*Intent intent = Intent(this, MainActivity.class);
            startActivity(intent);*/

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

}