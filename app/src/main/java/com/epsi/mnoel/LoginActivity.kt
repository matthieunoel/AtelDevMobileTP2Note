package com.epsi.mnoel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class LoginActivity : AppCompatActivity() {

    private var emailContent = ""
    private var passwordContent = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Firebase.initialize(application)
        auth = Firebase.auth

        val submitBtn = findViewById<Button>(R.id.btn_submit)

        submitBtn.setOnClickListener {
            clickConnection()
        }

    }

    private fun clickConnection() {
        var formUsername = (findViewById<EditText>(R.id.form_username)).text.toString()
        var formPassword = (findViewById<EditText>(R.id.form_password)).text.toString()

        if (formUsername != "") {

            if (formPassword == "") {
                formPassword = " "
            }

            auth.signInWithEmailAndPassword(formUsername, formPassword).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                            baseContext, "Connexion reussie.",
                            Toast.LENGTH_SHORT
                    ).show()

                    val user = auth.currentUser

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()

                } else {
                    this.alert("Erreur de connection", "Le couple Username/Password est incorrect.")
                }
            }

        }
        else {
            this.alert("Nom d'utilisateur", "Erreur : ce champ ne peut pas être vide.")
        }
    }

    private fun alert(alertTitle: String, alertMessage: String) {
        AlertDialog.Builder(this).setTitle(alertTitle).setMessage(alertMessage).show()
    }

}