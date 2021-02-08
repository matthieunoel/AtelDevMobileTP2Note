package com.epsi.mnoel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.android.synthetic.main.activity_main.*

//  https://console.firebase.google.com/u/0/project/ateldevmobile-tp2/settings/general/android:com.epsi.mnoel
//  https://www.codevscolor.com/android-kotlin-create-basic-recyclerview

class MainActivity : AppCompatActivity() {

    val values = arrayOf("01", "Deux (plus du texte de test. Tu sais comment c'est lol ^^ . . . Anyway va te faire foutre ? Ouuaaaaaais mdr)" , "Trois", "Quatre", "05" , "Six", "07", "08", "Neuf", "10", "11", "Douze", "13", "14", "Quinze", "16", "Dix-sept", "18", "19", "Vingt", "21", "Vingt deux", "23", "24", "25", "26", "Vingt sept", "28", "29", "Trente")

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this))
        rv.setAdapter(new MyAdapter());*/

        Firebase.initialize(application)

        var manager = LinearLayoutManager(this)
        var adpater = AdapterDuTurfu(values)

        recycler_view.adapter = adpater
        recycler_view.layoutManager = manager

        /*recycler_view = findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = manager
            adapter =  adpater
        }*/

//        list_test.layoutManager = LinearLayoutManager(this)

    }
}

class AdapterDuTurfu(private val myDataSet : Array<String>):
    RecyclerView.Adapter<AdapterDuTurfu.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(text: String){
            val tv = view.findViewById<TextView>(R.id.titleView)
            tv.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent,false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataSet[position])
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

}

data class Item(val id: Long, val title: String, val url: String)