package com.epsi.mnoel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_livre.*


class LivreActivity : AppCompatActivity() {

    private lateinit var livre: Livre

    override fun onCreate(savedInstanceState: Bundle?) {

        this.livre = Livre(intent.getIntExtra("id", 0), intent.getStringExtra("titre"), intent.getStringExtra("desc"), intent.getStringExtra("auteur"), intent.getStringExtra("img"))

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livre)

//        Log.i("MNOELREADME", "livre C : ${livre.toString()}")

        if (livre != null) {
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

//        Log.i("MNOELREADTHIS", "thereIsLivre : ${getSharedPreferences("listeLivresLus", Context.MODE_PRIVATE).getString("value", null).toString().split(",").contains(this.livre.id.toString()).toString()}")

        if (getSharedPreferences("listeLivresLus", Context.MODE_PRIVATE).getString("value", "").toString().split(",").contains(this.livre.id.toString())) {
            this.checkBox1.isChecked = true
        }

        this.checkBox1.setOnClickListener {
//            Log.i("MNOELREADTHIS", "CheckBox clicked !")
            if (this.checkBox1.isChecked) {
                val sp = getSharedPreferences("listeLivresLus", Context.MODE_PRIVATE);
                var listeLivresLus: String = sp.getString("value", null).toString()

                if (listeLivresLus != null && listeLivresLus != "") {
                    listeLivresLus += ",${this.livre.id.toString()}"
                }
                else {
                    listeLivresLus = this.livre.id.toString()
                }

//                Log.i("MNOELLISTE", "listeLivresLus : $listeLivresLus")

                val ed = sp.edit()
                ed.putString("value", listeLivresLus)
                ed.apply()
            }
            else {
                val sp = getSharedPreferences("listeLivresLus", Context.MODE_PRIVATE);

                val listeLivresLusOld: List<String> = sp.getString("value", null).toString().split(",")
                var listeLivresLusNew = ""
//                var founded: Boolean = false
                for (livreId in listeLivresLusOld) {
                    if (livreId != this.livre.id.toString()) {
                        listeLivresLusNew += ",$livreId"
                    }
                }

                if (listeLivresLusNew != "") {
                    listeLivresLusNew = listeLivresLusNew.substring(1)
                }

//                Log.i("MNOELLISTE", "listeLivresLusNew : $listeLivresLusNew")

                val ed = sp.edit()
                ed.putString("value", listeLivresLusNew)
                ed.apply()
            }
        }


    }

}