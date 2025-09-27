package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Firebase.firestore

        val note = findViewById<EditText>(R.id.notes)
        val button = findViewById<Button>(R.id.create)
        val next =findViewById<Button>(R.id.nextpage)

        next.setOnClickListener {
            startActivity(Intent(this, ViewPage::class.java))
        }

        button.setOnClickListener{
            val noteText = note.text.toString()

            val notes = hashMapOf(
                "Note" to noteText,
            )

            db.collection("Notes")

                .add(notes)
                .addOnSuccessListener { Toast.makeText(applicationContext,"Document inserted",Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(applicationContext,"Document insertion failed",Toast.LENGTH_SHORT).show() }

        }
    }
}