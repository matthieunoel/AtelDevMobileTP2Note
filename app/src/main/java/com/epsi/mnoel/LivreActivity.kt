package com.epsi.mnoel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_livre.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LivreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        val livre: Livre? = intent.getSerializableExtra("Editing") as Livre?

        val livre: Livre? = Livre(intent.getIntExtra("id", 0), intent.getStringExtra("titre"), intent.getStringExtra("desc"), intent.getStringExtra("auteur"), intent.getStringExtra("img"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livre)

        /*if (livre != null) {
            intent.putExtra("id", livre.id)
            intent.putExtra("titre", livre.titre)
            intent.putExtra("desc", livre.desc)
            intent.putExtra("auteur", livre.auteur)
            intent.putExtra("img", livre.img)
        }*/
        Log.i("MNOELREADME", "livre C : ${livre.toString()}")

        if (livre != null) {
//            val titre = livre.titre + "\r\n" + livre.auteur
//            Log.i("MNOELREADME", "titre : $titre")
//            val titre = this.findViewById<TextView>(R.id.titre)
//            this.titre.text = "Ouioui"
            this.titre.text = livre.titre + "\r\n" + livre.auteur
            this.desc.text = livre.desc
            if (livre.img != null) {
                try{
                    val gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ateldevmobile-tp2.appspot.com" + livre.img)
                    Glide.with(this).load(gsReference).into(imageView)
                } catch (ex: Exception) {
                    Log.e("MNOELREADME", "Error message : ${ex.message}")
                    imageView.visibility = View.GONE
                }
            }
        }





    }

}