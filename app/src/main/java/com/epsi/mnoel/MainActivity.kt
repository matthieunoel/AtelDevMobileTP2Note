package com.epsi.mnoel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val values = arrayOf("one", "two" , "three", "four", "five" , "six", "seven", "eight", "nine", "ten")

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*RecyclerView rv = (RecyclerView) findViewById(R.id.list);
        rv.setLayoutManager(new LinearLayoutManager(this))
        rv.setAdapter(new MyAdapter());*/

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
            val tv = view.findViewById<TextView>(R.id.textView)
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