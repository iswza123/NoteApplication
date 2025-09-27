package com.example.notesapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class ViewPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notelist : ArrayList<Notes>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        notelist = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("Notes").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (data in document.documents) {
                        val note: Notes? = data.toObject(Notes::class.java)
                        if (note != null) {
                            val noteID = note.copy(document = data.id)
                            notelist.add(noteID)
                        }
                    }
                    // Debugging: Print fetched notes
                    Log.d("FirestoreData", "Notes fetched: $notelist")

                    recyclerView.adapter = Adapter(notelist)
                    recyclerView.adapter?.notifyDataSetChanged() // Ensure UI updates
                }
            }
            .addOnFailureListener {
                Log.e("FirestoreData", "Error fetching data", it)
                Toast.makeText(applicationContext, "Error while loading data",
                    Toast.LENGTH_SHORT).show()
            }



    }
}