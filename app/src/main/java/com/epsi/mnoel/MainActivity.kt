package com.epsi.mnoel

import android.content.Context
import android.os.Bundle
import android.text.Editable
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
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream
import com.epsi.mnoel.FireBaseCompatibleGlideModule

//import java.lang.reflect.Array


//  https://console.firebase.google.com/u/0/project/ateldevmobile-tp2/settings/general/android:com.epsi.mnoel
//  https://www.codevscolor.com/android-kotlin-create-basic-recyclerview

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    private lateinit var listeLivres: Array<Livre>

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

                var manager = LinearLayoutManager(this@MainActivity)
                var adpater = AdapterDuTurfu(this@MainActivity.listeLivres)

                this@MainActivity.recycler_view.adapter = adpater
                this@MainActivity.recycler_view.layoutManager = manager

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("MNOELREADTHIS", "onCancelled: Error: " + databaseError.message)
            }

            override fun toString(): String {
                return "FUCK YOU !"
            }
        })

        dbLivresRef.get()

        var manager = LinearLayoutManager(this)
        var adpater = AdapterDuTurfu(
            arrayOf(
                Livre(
                    0,
                    "Chargement en cours ...",
                    "Chargement en cours ..."
                )
            )
        )

        this.recycler_view.adapter = adpater
        this.recycler_view.layoutManager = manager

        /* ##### */

//        var storage:FirebaseStorage  = FirebaseStorage.getInstance()
//
//        var livre1ImgRef = storage.getReference()
//
//        var img = livre1ImgRef.child("livre_1.png")
//
//        Log.i("MNOELREADTHIS", "Livre1Img : " + img)

        /* ##### */

    }
}

class AdapterDuTurfu(private val myDataSet: Array<Livre>):
    RecyclerView.Adapter<AdapterDuTurfu.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(livre: Livre){
            val tv = view.findViewById<TextView>(R.id.titleView)

            if (livre.auteur != null) {
                tv.text = livre.titre + "\r\n" + livre.auteur
            }
            else {
                tv.text = livre.titre
            }

            /*if (livre.img != null) {
//                view.findViewById<ImageView>(R.id.imageView).setImageURI(Uri.parse(livre.img))

                val imageBytes = Base64.decode(livre.img, Base64.DEFAULT)
                Log.i("MNOELREADTHIS", "imageBytes : $imageBytes")
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                Log.i("MNOELREADTHIS", "decodedImage : $decodedImage")
//                view.findViewById<ImageView>(R.id.imageView).setImageBitmap(decodedImage)
                view.findViewById<ImageView>(R.id.imageView).setImageBitmap(decodedImage)
            }*/

            /* ##### */

            /*var storage:FirebaseStorage  = FirebaseStorage.getInstance()

            var livre1ImgRef = storage.getReference()

            var img = livre1ImgRef.child("images/livre_1.jpg")

            Log.i("MNOELREADTHIS", "Livre1Img : " + img)*/

            /* ##### */

            /*// Reference to an image file in Cloud Storage
//            StorageReference storageReference = = FirebaseStorage.getInstance().getReference().child("yourImageReferencePath");

            var storageReference:Any  = FirebaseStorage.getInstance().getReference().child("images/livre_1.jpg");
            Log.i("MNOELREADTHIS", "storageReference : $storageReference")

//            ImageView image = (ImageView)findViewById(R.id.imageView);

            var image: ImageView = view.findViewById<ImageView>(R.id.imageView)
            Log.i("MNOELREADTHIS", "image : $image")

            // Load the image using Glide
            Glide.with(this.view)
//                .using(FirebaseImageLoader())
                .load(storageReference)
                .into(image)*/

            /* ##### */

            // WORKING CODE!
            val storage = FirebaseStorage.getInstance()

            // Create a reference to a file from a Google Cloud Storage URI
            val gsReference = storage.getReferenceFromUrl("gs://ateldevmobile-tp2.appspot.com/images/livre_1.jpg")

            var imageView: ImageView = view.findViewById<ImageView>(R.id.imageView)

            /*GlideApp.with(this.view)
                .load(gsReference)
                .into(imageView)*/

            Glide.with(this.view)
                .load(gsReference)
                .into(imageView)

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

class Livre {
    var id: Int? = null
    var titre: String? = null
    var desc: String? = null
    var auteur: String? = null
    var img: String? = null

    constructor() {}
    constructor(
        id: Int?,
        titre: String?,
        desc: String?
    ) {
        this.id = id
        this.titre = titre
        this.desc = desc
    }
    constructor(
        id: Int?,
        titre: String?,
        desc: String?,
        auteur: String?,
        img: String?
    ) {
        this.id = id
        this.titre = titre
        this.desc = desc
        this.auteur = auteur
        this.img = img
    }
}