package com.epsi.mnoel

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_livre.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var listeLivres: Array<Livre>
    private var isTotalyInit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Firebase.initialize(application)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

        val dbLivresRef = database.getReference("livre")

        this.listeLivres = arrayOf()

        dbLivresRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {

                    val livre: Livre? = data.getValue(Livre::class.java)

                    if (livre != null) {
                        this@MainActivity.listeLivres += livre
                    }
                }

                var adpater = AdapterDuTurfu(this@MainActivity.listeLivres)
                this@MainActivity.recycler_view.adapter = adpater

                this@MainActivity.isTotalyInit = true

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MNOELREADTHIS", "onCancelled: Error: " + databaseError.message)
            }
        })

        dbLivresRef.get()

        var manager = LinearLayoutManager(this)
        var adpater = AdapterDuTurfu(arrayOf(Livre(-1, "Chargement en cours ...", "Chargement en cours ...")))

        this.recycler_view.adapter = adpater
        this.recycler_view.layoutManager = manager

    }

    override fun onResume() {
        super.onResume();
        if (this.isTotalyInit) {
            var adpater = AdapterDuTurfu(this@MainActivity.listeLivres)
            this@MainActivity.recycler_view.adapter = adpater
        }
    }

    fun navigateToLivre(livre: Livre) {
//        Log.i("MNOELREADME", "livre B : ${livre.toString()}")
        val intent = Intent(this, LivreActivity::class.java)
        intent.putExtra("id", livre.id)
        intent.putExtra("titre", livre.titre)
        intent.putExtra("desc", livre.desc)
        intent.putExtra("auteur", livre.auteur)
        intent.putExtra("img", livre.img)
        startActivity(intent)
    }

}

class AdapterDuTurfu(private val myDataSet: Array<Livre>):
    RecyclerView.Adapter<AdapterDuTurfu.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(livre: Livre){
            val titleView = view.findViewById<TextView>(R.id.titleView)
            var imageView: ImageView = view.findViewById<ImageView>(R.id.imageView)

            if (livre.auteur != null) {
                titleView.text = livre.titre + "\r\n" + livre.auteur
            }
            else {
                titleView.text = livre.titre
            }

            if (this.getActivity()?.getSharedPreferences("listeLivresLus", Context.MODE_PRIVATE)?.getString("value", "").toString().split(",").contains(livre.id.toString())) {
                this.view.titleView.background = ColorDrawable(0xFF97C908.toInt())
                this.view.titleView.setTextColor(0xFF4A7600.toInt())
            }
            else if (livre.id != -1) {
                this.view.titleView.setBackground(ColorDrawable(0xFFF84B44.toInt()))
                this.view.titleView.setTextColor(0xFF8A2415.toInt())
            }

            if (livre.img != null) {
                try{
                    val gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ateldevmobile-tp2.appspot.com" + livre.img)
                    Glide.with(this.view).load(gsReference).into(imageView)
                } catch (ex: Exception) {
                    Log.e("MNOELREADME", "Error message : ${ex.message}")
                    imageView.setImageDrawable(R.drawable.img_error)
                }
            }
            else {
                this.view.imageView.visibility = View.GONE
                this.view.loading.visibility = View.VISIBLE
            }

            this.view.setOnClickListener {
//                Log.i("MNOELREADTHIS", "CLICK!")
//                Log.i("MNOELREADME", "livre A : ${livre.toString()}")
                this.getActivity()?.navigateToLivre(livre)
            }

        }

        private fun getActivity(): MainActivity? {
            var context: Context = this.view.context
            while (context is ContextWrapper) {
                if (context is MainActivity) {
                    return context
                }
                context = (context).baseContext
            }
            return null
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataSet[position])
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

}

private fun Any.setImageDrawable(imgError: Int) {
    TODO("Not yet implemented")
    // What am I supposed to do ?!
}