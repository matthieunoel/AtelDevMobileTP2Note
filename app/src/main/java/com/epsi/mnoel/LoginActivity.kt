package com.epsi.mnoel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*this.form_username.setText("matthieu.noel@epsi.fr")
        this.form_password.setText("passpass")*/

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

        if (formUsername != "" && formPassword != "") {

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

                    Log.i("MNOELREADTHIS", "userUid : ${user?.uid}")

                    val sp = getSharedPreferences("userUid", Context.MODE_PRIVATE);
                    val ed = sp.edit()
                    ed.putString("value", user?.uid)
                    ed.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()

                } else {
                    this.alert("Erreur de connection", "Mauvais login / mot de passe")
                }
            }

        }
        else if(formUsername == "" && formPassword != "") {
            this.alert("Erreur de connection", "L'email est vide");
        }
        else if(formPassword == "" && formUsername != "") {
            this.alert("Erreur de connection", "Le mot de passe est vide");
        }
        else {
            this.alert("Erreur de connection", "Les champs sont vides");
        }
    }

    private fun alert(alertTitle: String, alertMessage: String) {
        AlertDialog.Builder(this).setTitle(alertTitle).setMessage(alertMessage).show()
    }

}